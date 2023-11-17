package dev.forgearchive.index;

import dev.forgearchive.core.*;
public final class IndexHeader {
    public static final int MAGIC = 0x49445831;
    private final int version;
    private final long entryCount;
    private final ContentHash rootHash;

    public IndexHeader(int version, long entryCount, ContentHash rootHash) {
        this.version = version;
        this.entryCount = entryCount;
        this.rootHash = rootHash;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        w.writeInt(MAGIC);
        w.writeInt(version);
        w.writeLong(entryCount);
        w.writeBytes(rootHash.bytes());
        return w.toByteArray();
    }

    public static IndexHeader decode(byte[] data) throws Exception {
        BinaryReader r = BinaryReader.wrap(data);
        if (r.readInt() != MAGIC) throw new IllegalArgumentException("bad index magic");
        return new IndexHeader(r.readInt(), r.readLong(), ContentHash.ofDigest(r.readBytes(32)));
    }

    public long entryCount() { return entryCount; }

}
