package dev.forgearchive.core;

import java.util.zip.CRC32C;

public final class Checksum {
    private final CRC32C crc = new CRC32C();
    private long bytes;

    public void update(byte[] data, int off, int len) {
        crc.update(data, off, len);
        bytes += len;
    }

    public void update(byte b) {
        crc.update(b);
        bytes++;
    }

    public long value() { return crc.getValue(); }
    public long bytesProcessed() { return bytes; }

    public static long crc32c(byte[] data) {
        Checksum c = new Checksum();
        c.update(data, 0, data.length);
        return c.value();
    }

    public Checksum fork() {
        Checksum c = new Checksum();
        c.crc.reset();
        c.bytes = bytes;
        return c;
    }
}
