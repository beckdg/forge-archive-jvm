package dev.forgearchive.fuzz;

import dev.forgearchive.snapshot.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class SnapshotFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            if (data.length >= 52) SnapshotHeader.decode(java.util.Arrays.copyOf(data, 52));
            new SnapshotReader().open(data);
            new SnapshotValidator().validate(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
