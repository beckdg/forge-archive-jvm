package dev.forgearchive.archive;


import dev.forgearchive.archive.*;
import dev.forgearchive.compression.*;
import dev.forgearchive.core.*;
import java.io.*;
import java.util.*;

public final class ArchiveWriter {
    private final File target;
    private final List<FarEntry> entries = new ArrayList<>();
    private final RandomAccessFile raf;
    private long dataOffset;

    public ArchiveWriter(File target) throws IOException {
        this.target = target;
        raf = new RandomAccessFile(target, "rw");
        dataOffset = 64;
        raf.seek(dataOffset);
    }

    public void addEntry(String path, byte[] data, CompressionCodec codec) throws Exception {
        byte[] framed = new FramedCompressor().frame(codec, data);
        long off = raf.getFilePointer();
        raf.write(framed);
        FarEntry e = new FarEntry(path, off, framed.length, data.length,
            ContentHash.sha256(data), codec.id());
        entries.add(e);
    }

    public void addEntry(String path, byte[] data) throws Exception {
        addEntry(path, data, CompressionCodec.LZ4);
    }

    public void finish() throws Exception {
        BinaryWriter manifest = new BinaryWriter();
        manifest.writeVarInt(entries.size());
        for (FarEntry e : entries) manifest.writeBytes(e.encode());
        byte[] manifestBytes = manifest.toByteArray();
        FarHeader header = new FarHeader(0, System.currentTimeMillis(),
            ContentHash.sha256(manifestBytes));
        raf.seek(0);
        raf.write(header.encode());
        raf.write(manifestBytes);
        raf.close();
    }

    public void close() throws Exception {
        finish();
    }

}
