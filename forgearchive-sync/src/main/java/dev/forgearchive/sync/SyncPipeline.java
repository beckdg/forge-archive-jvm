package dev.forgearchive.sync;

import dev.forgearchive.diff.BinaryDiff;
import java.io.*;
import java.nio.file.Files;

public final class SyncPipeline {

    public void push(File localArchive, java.net.URI remote) throws Exception {
        SyncEngine engine = new SyncEngine();
        byte[] local = Files.readAllBytes(localArchive.toPath());
        if (remote.getScheme().equals("file")) {
            engine.diff(localArchive, new File(remote));
        }
        new BinaryDiff().diff(local, local);
    }
}
