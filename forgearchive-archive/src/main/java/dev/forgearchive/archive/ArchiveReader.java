package dev.forgearchive.archive;


import dev.forgearchive.compression.*;
import dev.forgearchive.core.*;
import dev.forgearchive.io.SeekableInput;
import java.io.*;
import java.util.*;

public final class ArchiveReader {
    private final SeekableInput input;
    private final FarHeader header;
    private final List<FarEntry> entries;

    public ArchiveReader(File file) throws Exception {
        input = new SeekableInput(file);
        byte[] hdr = new byte[64];
        input.read(hdr, 0, hdr.length);
        header = FarHeader.decode(hdr);
        byte[] rest = readAllAfterHeader(file);
        BinaryReader r = BinaryReader.wrap(rest);
        int count = r.readVarInt();
        entries = new ArrayList<>(count);
        for (int i = 0; i < count; i++) entries.add(FarEntry.decode(r));
    }

    private byte[] readAllAfterHeader(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.skip(64);
            return fis.readAllBytes();
        }
    }

    public List<FarEntry> entries() { return Collections.unmodifiableList(entries); }
    public FarHeader header() { return header; }

    public byte[] readEntry(FarEntry entry) throws Exception {
        input.seek(entry.offset());
        byte[] framed = new byte[(int) entry.compressedSize()];
        input.read(framed, 0, framed.length);
        byte[] data = new FramedCompressor().unframe(framed);
        if (!ContentHash.sha256(data).equals(entry.hash()))
            throw new ForgeFormatException("HASH", "entry hash mismatch: " + entry.path());
        return data;
    }

}
