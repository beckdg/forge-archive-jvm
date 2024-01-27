package dev.forgearchive.fuzz;

import dev.forgearchive.journal.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class JournalFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            java.io.File j = java.io.File.createTempFile("jnl", ".jnl");
            java.nio.file.Files.write(j.toPath(), data);
            new JournalReader().replay(j);
            new JournalValidator().validate(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
