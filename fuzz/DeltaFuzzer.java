package dev.forgearchive.fuzz;

import dev.forgearchive.diff.*;
import dev.forgearchive.chunking.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class DeltaFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            new DeltaEncoder().encode(data, data);
            new FastCDC().chunk(data);
            new RabinFingerprint().roll(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
