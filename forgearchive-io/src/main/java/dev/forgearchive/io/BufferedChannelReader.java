package dev.forgearchive.io;

import java.io.*; import java.nio.ByteBuffer; import java.nio.channels.*;
public final class BufferedChannelReader {
    private final ReadableByteChannel channel;
    private final byte[] buf = new byte[8192];
    private int pos;
    private int limit;

    public BufferedChannelReader(ReadableByteChannel channel) { this.channel = channel; }

    public int read(byte[] out, int off, int len) throws IOException {
        if (pos >= limit) {
            limit = channel.read(ByteBuffer.wrap(buf));
            pos = 0;
            if (limit <= 0) return -1;
        }
        int n = Math.min(len, limit - pos);
        System.arraycopy(buf, pos, out, off, n);
        pos += n;
        return n;
    }

}
