package dev.forgearchive.sync;


import dev.forgearchive.archive.*;
import java.io.*;
import java.util.*;

public final class SyncEngine {
    public List<String> diff(File localArchive, File remoteArchive) throws Exception {
        ArchiveReader local = new ArchiveReader(localArchive);
        ArchiveReader remote = new ArchiveReader(remoteArchive);
        Set<String> localPaths = new HashSet<>();
        local.entries().forEach(e -> localPaths.add(e.path()));
        List<String> toFetch = new ArrayList<>();
        for (FarEntry e : remote.entries()) {
            if (!localPaths.contains(e.path())) toFetch.add(e.path());
        }
        return toFetch;
    }

}
