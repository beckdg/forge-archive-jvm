package dev.forgearchive.fuzz;

import dev.forgearchive.stream.*;
import dev.forgearchive.archive.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class StreamReaderFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            new ObjectStreamReader().readObjects(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
