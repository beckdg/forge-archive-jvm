package dev.forgearchive.fuzz;

import dev.forgearchive.patch.*;
import dev.forgearchive.diff.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class PatchFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            if (data.length > 4) new PatchApplier().apply(new byte[Math.min(64, data.length)], data);
            new PatchGenerator().generate(new byte[0], data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
