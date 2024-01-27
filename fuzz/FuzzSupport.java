package dev.forgearchive.fuzz;

import dev.forgearchive.core.*;
import java.util.*;

/** Shared utilities for cross-module fuzz harness orchestration. */
public final class FuzzSupport {
    private FuzzSupport() {}

    public static byte[] slice(byte[] data, int max) {
        if (data.length <= max) return data;
        return Arrays.copyOf(data, max);
    }

    public static void roundTripBinary(byte[] data, dev.forgearchive.core.BinaryWriter w) {
        try {
            BinaryReader r = BinaryReader.wrap(data);
            while (r.hasRemaining()) {
                w.writeByte(r.readByte());
            }
        } catch (Exception ignored) {
            w.writeBytes(data);
        }
    }

    public static java.io.File tempFile(String prefix, byte[] data) throws java.io.IOException {
        java.io.File f = java.io.File.createTempFile(prefix, ".bin");
        f.deleteOnExit();
        java.nio.file.Files.write(f.toPath(), data);
        return f;
    }
}
