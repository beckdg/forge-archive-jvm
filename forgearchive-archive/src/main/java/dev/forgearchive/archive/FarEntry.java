package dev.forgearchive.archive;


import dev.forgearchive.core.*;

public final class FarEntry {
    private final String path;
    private final long offset;
    private final long compressedSize;
    private final long uncompressedSize;
    private final ContentHash hash;
    private final int codecId;

    public FarEntry(String path, long offset, long csize, long usize, ContentHash hash, int codecId) {
        this.path = path;
        this.offset = offset;
        this.compressedSize = csize;
        this.uncompressedSize = usize;
        this.hash = hash;
        this.codecId = codecId;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        byte[] pathBytes = path.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        w.writeVarInt(pathBytes.length);
        w.writeBytes(pathBytes);
        w.writeLong(offset);
        w.writeLong(compressedSize);
        w.writeLong(uncompressedSize);
        w.writeBytes(hash.bytes());
        w.writeInt(codecId);
        return w.toByteArray();
    }

    public static FarEntry decode(BinaryReader r) throws Exception {
        int plen = r.readVarInt();
        String path = r.readUtf8(plen);
        long off = r.readLong();
        long cs = r.readLong();
        long us = r.readLong();
        ContentHash h = ContentHash.ofDigest(r.readBytes(32));
        int codec = r.readInt();
        return new FarEntry(path, off, cs, us, h, codec);
    }

    public String path() { return path; }
    public long offset() { return offset; }
    public long compressedSize() { return compressedSize; }
    public long uncompressedSize() { return uncompressedSize; }
    public ContentHash hash() { return hash; }
    public int codecId() { return codecId; }

    public int encodedSize() {
        byte[] pathBytes = path.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        return VarInt.sizeUnsigned(pathBytes.length) + pathBytes.length + 8 + 8 + 8 + 32 + 4;
    }

}
