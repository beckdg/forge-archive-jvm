package dev.forgearchive.chunking;


public final class RollingHash {
    private static final long BASE = 257;
    private final int window;
    private final byte[] buf;
    private int pos;
    private long hash;

    public RollingHash(int window) {
        this.window = window;
        buf = new byte[window];
    }

    public void push(byte b) {
        if (pos < window) {
            buf[pos++] = b;
            hash = hash * BASE + (b & 0xFF);
        } else {
            byte old = buf[pos % window];
            buf[pos % window] = b;
            hash = hash * BASE + (b & 0xFF) - old * pow(BASE, window);
            pos++;
        }
    }

    public long hash() { return hash; }

    private static long pow(long b, int e) {
        long r = 1;
        for (int i = 0; i < e; i++) r *= b;
        return r;
    }

}
