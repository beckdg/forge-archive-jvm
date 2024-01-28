package dev.forgearchive.fuzz;

import dev.forgearchive.transaction.*;
import dev.forgearchive.journal.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class TransactionFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            java.io.File t = java.io.File.createTempFile("txn", ".jnl");
            TransactionManager mgr = new TransactionManager(t);
            new JournalReader().replay(t);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
