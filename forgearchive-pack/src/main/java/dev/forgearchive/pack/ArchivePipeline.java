package dev.forgearchive.pack;

import dev.forgearchive.archive.*;
import dev.forgearchive.chunking.*;
import dev.forgearchive.dedup.*;
import java.io.*;

public final class ArchivePipeline {

    public void execute(File source, File dest) throws Exception {
        ContentDefinedChunker chunker = new ContentDefinedChunker();
        ContentAddressedStore store = new ContentAddressedStore();
        ArchiveWriter writer = new ArchiveWriter(dest);
        try (var stream = java.nio.file.Files.walk(source.toPath())) {
            stream.filter(p -> !java.nio.file.Files.isDirectory(p)).forEach(p -> {
                try {
                    byte[] data = java.nio.file.Files.readAllBytes(p);
                    for (ContentDefinedChunker.Chunk chunk : chunker.chunk(data)) {
                        store.store(chunk.data());
                    }
                    String rel = source.toPath().relativize(p).toString();
                    writer.addEntry(rel, data);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        writer.close();
    }
}
