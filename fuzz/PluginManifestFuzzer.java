package dev.forgearchive.fuzz;

import dev.forgearchive.plugin.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class PluginManifestFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            try { PluginDescriptor.decode(data); } catch (Exception ignored) {}
            new PluginLoader().register(new PluginDescriptor("f", "1", ContentHash.sha256(data)));
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
