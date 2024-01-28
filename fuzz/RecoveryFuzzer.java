package dev.forgearchive.fuzz;

import dev.forgearchive.recovery.*;
import dev.forgearchive.journal.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class RecoveryFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            java.io.File j = java.io.File.createTempFile("rec", ".jnl");
            java.nio.file.Files.write(j.toPath(), data);
            new RecoveryManager().recover(j, j.getParentFile());
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
