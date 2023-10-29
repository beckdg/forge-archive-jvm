package dev.forgearchive.core;

public final class BitUtil {
    private BitUtil() {}

    public static int roundUpPow2(int v) {
        v--;
        v |= v >>> 1;
        v |= v >>> 2;
        v |= v >>> 4;
        v |= v >>> 8;
        v |= v >>> 16;
        return v + 1;
    }

    public static int popCount(int x) { return Integer.bitCount(x); }
    public static int log2Floor(int v) { return 31 - Integer.numberOfLeadingZeros(v); }

    public static long getLong(byte[] b, int off) {
        return ((long) b[off] & 0xFF) | (((long) b[off + 1] & 0xFF) << 8)
            | (((long) b[off + 2] & 0xFF) << 16) | (((long) b[off + 3] & 0xFF) << 24)
            | (((long) b[off + 4] & 0xFF) << 32) | (((long) b[off + 5] & 0xFF) << 40)
            | (((long) b[off + 6] & 0xFF) << 48) | (((long) b[off + 7] & 0xFF) << 56);
    }

    public static void putLong(byte[] b, int off, long v) {
        b[off] = (byte) v;
        b[off + 1] = (byte) (v >>> 8);
        b[off + 2] = (byte) (v >>> 16);
        b[off + 3] = (byte) (v >>> 24);
        b[off + 4] = (byte) (v >>> 32);
        b[off + 5] = (byte) (v >>> 40);
        b[off + 6] = (byte) (v >>> 48);
        b[off + 7] = (byte) (v >>> 56);
    }
}
