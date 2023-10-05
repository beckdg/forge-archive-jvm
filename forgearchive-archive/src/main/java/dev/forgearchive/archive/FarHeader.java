package dev.forgearchive.archive;


import dev.forgearchive.core.*;

public final class FarHeader {
    public static final int MAGIC = 0x46415231;
    public static final int VERSION = 1;
    private final int flags;
    private final long created;
    private final ContentHash manifestHash;

    public FarHeader(int flags, long created, ContentHash manifestHash) {
        this.flags = flags;
        this.created = created;
        this.manifestHash = manifestHash;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        w.writeInt(MAGIC);
        w.writeInt(VERSION);
        w.writeInt(flags);
        w.writeLong(created);
        w.writeBytes(manifestHash.bytes());
        w.writeLong(Checksum.crc32c(w.toByteArray()));
        return w.toByteArray();
    }

    public static FarHeader decode(byte[] data) throws Exception {
        BinaryReader r = BinaryReader.wrap(data);
        int magic = r.readInt();
        if (magic != MAGIC) throw new ForgeFormatException("BAD_MAGIC", "invalid FAR magic");
        int ver = r.readInt();
        if (ver != VERSION) throw new ForgeFormatException("BAD_VERSION", "unsupported version " + ver);
        int flags = r.readInt();
        long created = r.readLong();
        byte[] hash = r.readBytes(32);
        return new FarHeader(flags, created, ContentHash.ofDigest(hash));
    }

    public int magic() { return MAGIC; }
    public int flags() { return flags; }
    public long created() { return created; }
    public ContentHash manifestHash() { return manifestHash; }

    public long headerCrc() {
        return Checksum.crc32c(encode());
    }

}
