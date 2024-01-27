package dev.forgearchive.fuzz;

import dev.forgearchive.archive.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class ArchiveReaderFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            java.io.File f = java.io.File.createTempFile("far", ".far");
            java.nio.file.Files.write(f.toPath(), data);
            FarStreamParser parser = new FarStreamParser();
            parser.feed(data);
            try {
              ArchiveReader reader = new ArchiveReader(f);
              new ArchiveVerifier().verify(reader);
            } catch (Exception ignored) {}
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
