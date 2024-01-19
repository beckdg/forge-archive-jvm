package dev.forgearchive.unpack;


import dev.forgearchive.archive.*;
import java.io.*;

public final class ArchiveUnpacker {
    public void extract(File archive, File destDir) throws Exception {
        ArchiveReader reader = new ArchiveReader(archive);
        for (FarEntry e : reader.entries()) {
            File out = new File(destDir, e.path());
            out.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(out)) {
                fos.write(reader.readEntry(e));
            }
        }
    }

}
