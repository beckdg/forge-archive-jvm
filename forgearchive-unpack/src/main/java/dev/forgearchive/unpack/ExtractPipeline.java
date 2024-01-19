package dev.forgearchive.unpack;

import dev.forgearchive.archive.*;
import dev.forgearchive.recovery.*;
import java.io.*;

public final class ExtractPipeline {

    public void execute(File archive, File dest) throws Exception {
        ArchiveReader reader = new ArchiveReader(archive);
        ArchiveUnpacker unpacker = new ArchiveUnpacker();
        unpacker.extract(archive, dest);
        ArchiveVerifier verifier = new ArchiveVerifier();
        var errors = verifier.verify(reader);
        if (!errors.isEmpty()) {
            RecoveryManager recovery = new RecoveryManager();
            File journal = new File(dest, ".forge/journal");
            if (journal.exists()) {
                recovery.recover(journal, dest);
            }
        }
    }
}
