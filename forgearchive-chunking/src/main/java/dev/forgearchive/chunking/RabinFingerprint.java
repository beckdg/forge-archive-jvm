package dev.forgearchive.chunking;


public final class RabinFingerprint {
    private static final int MOD = 16777619;
    private long state = 0;

    public void reset() { state = 0; }

    public void update(byte b) {
        state = ((state << 1) | (b & 1)) % MOD;
        state ^= (b & 0xFF) * 0x811C9DC5L;
    }

    public long value() { return state; }

    public static long fingerprint(byte[] data) {
        RabinFingerprint rf = new RabinFingerprint();
        for (byte b : data) rf.update(b);
        return rf.value();
    }

    public long roll(byte[] data) {
        reset();
        for (byte b : data) update(b);
        return value();
    }

}
