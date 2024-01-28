package dev.forgearchive.fuzz;

import dev.forgearchive.virtualfs.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class VirtualFsFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            VirtualFileSystem vfs = new VirtualFileSystem();
            vfs.writeFile("/fuzz/" + data.length, data);
            vfs.readFile("/fuzz/" + data.length);
            vfs.list("/");
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
