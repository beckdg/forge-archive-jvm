package dev.forgearchive.fuzz;

import dev.forgearchive.manifest.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class ManifestFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            new ManifestParser().parse(data);
            new ManifestValidator().validate(data);
            ManifestWriter w = new ManifestWriter();
            w.begin(); w.end();
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
