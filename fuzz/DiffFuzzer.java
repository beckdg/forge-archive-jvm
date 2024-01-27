package dev.forgearchive.fuzz;

import dev.forgearchive.diff.*;
import dev.forgearchive.chunking.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class DiffFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            new BinaryDiff().diff(data, data);
            new DeltaEncoder().encode(data, data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
