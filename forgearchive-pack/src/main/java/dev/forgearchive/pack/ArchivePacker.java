package dev.forgearchive.pack;


import dev.forgearchive.archive.*;
import dev.forgearchive.compression.*;
import java.io.*;

public final class ArchivePacker {
    public void pack(File sourceDir, File archiveFile) throws Exception {
        ArchiveWriter writer = new ArchiveWriter(archiveFile);
        packDir(sourceDir, sourceDir, writer);
        writer.finish();
    }

    private void packDir(File root, File dir, ArchiveWriter writer) throws Exception {
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.isDirectory()) packDir(root, f, writer);
            else {
                String rel = root.toPath().relativize(f.toPath()).toString().replace('\\', '/');
                byte[] data = new FileInputStream(f).readAllBytes();
                writer.addEntry(rel, data, CompressionCodec.ZSTD);
            }
        }
    }

}
