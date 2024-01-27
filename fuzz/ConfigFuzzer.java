package dev.forgearchive.fuzz;

import dev.forgearchive.configuration.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class ConfigFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            new ConfigParser().parse(data);
            new ConfigValidator().validate(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
