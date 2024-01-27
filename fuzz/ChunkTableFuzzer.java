package dev.forgearchive.fuzz;

import dev.forgearchive.dedup.*;
import dev.forgearchive.chunking.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class ChunkTableFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            ChunkTable table = new ChunkTable();
            table.decode(data);
            table.encode();
            new ContentDefinedChunker().chunk(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
