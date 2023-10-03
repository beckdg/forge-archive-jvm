package dev.forgearchive.api;


import dev.forgearchive.archive.*;
import dev.forgearchive.pack.*;
import dev.forgearchive.unpack.*;
import dev.forgearchive.inspection.*;
import java.io.*;
import java.util.*;

public final class ForgeArchive {
    public void create(File source, File archive) throws Exception {
        new ArchivePacker().pack(source, archive);
    }

    public void extract(File archive, File dest) throws Exception {
        new ArchiveUnpacker().extract(archive, dest);
    }

    public Map<String, Object> inspect(File archive) throws Exception {
        return new ArchiveInspector().inspect(new ArchiveReader(archive));
    }

    public java.util.List<String> verify(File archive) throws Exception {
        return new ArchiveVerifier().verify(new ArchiveReader(archive));
    }

}
