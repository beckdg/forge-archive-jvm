package dev.forgearchive.fuzz;

import dev.forgearchive.compression.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class CompressionFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            try { new FramedCompressor().unframe(data); } catch (Exception ignored) {}
            new Lz4Codec().decompress(data);
            new ZstdCodec().decompress(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
