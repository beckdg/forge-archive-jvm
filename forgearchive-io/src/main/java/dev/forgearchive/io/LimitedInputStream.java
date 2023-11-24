package dev.forgearchive.io;

import java.io.*;
public final class LimitedInputStream extends java.io.InputStream {
    private final InputStream in;
    private long remaining;

    public LimitedInputStream(InputStream in, long limit) {
        this.in = in;
        this.remaining = limit;
    }

    @Override
    public int read() throws IOException {
        if (remaining <= 0) return -1;
        int b = in.read();
        if (b >= 0) remaining--;
        return b;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (remaining <= 0) return -1;
        int toRead = (int) Math.min(len, remaining);
        int n = in.read(b, off, toRead);
        if (n > 0) remaining -= n;
        return n;
    }

}
