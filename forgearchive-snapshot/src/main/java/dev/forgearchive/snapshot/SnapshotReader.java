package dev.forgearchive.snapshot;

import java.io.*;
public final class SnapshotReader {
    public SnapshotHeader read(File file) throws Exception {
        byte[] hdr = new byte[52];
        try (FileInputStream fis = new FileInputStream(file)) {
            int n = fis.read(hdr);
            if (n < 52) throw new IOException("truncated snapshot");
        }
        return SnapshotHeader.decode(hdr);
    }

    public SnapshotHeader open(byte[] data) throws Exception {
        if (data.length < 52) throw new IOException("truncated snapshot bytes");
        return SnapshotHeader.decode(java.util.Arrays.copyOf(data, 52));
    }

}
