package dev.forgearchive.dedup;


public final class BloomFilter {
    private final BitSet bits;
    private final int numHashes;
    private final int size;

    static class BitSet {
        final byte[] data;
        BitSet(int bytes) { data = new byte[bytes]; }
        void set(int i) { data[i / 8] |= (1 << (i % 8)); }
        boolean get(int i) { return (data[i / 8] & (1 << (i % 8))) != 0; }
    }

    public BloomFilter(int expected, double fpp) {
        size = Math.max(64, (int) (-expected * Math.log(fpp) / (Math.log(2) * Math.log(2))));
        bits = new BitSet((size + 7) / 8);
        numHashes = Math.max(1, (int) Math.round((size / (double) expected) * Math.log(2)));
    }

    public BloomFilter(int expected) {
        this(expected, 0.01);
    }

    public void add(byte[] key) {
        for (int i = 0; i < numHashes; i++) {
            int h = hash(key, i) % size;
            if (h < 0) h += size;
            bits.set(h);
        }
    }

    public boolean mightContain(byte[] key) {
        for (int i = 0; i < numHashes; i++) {
            int h = hash(key, i) % size;
            if (h < 0) h += size;
            if (!bits.get(h)) return false;
        }
        return true;
    }

    private int hash(byte[] k, int seed) {
        int h = seed;
        for (byte b : k) h = 31 * h + b;
        return h;
    }

}
