package dev.forgearchive.snapshot;

import dev.forgearchive.core.*;
public final class SnapshotHeader {
    public static final int MAGIC = 0x534E4150;
    private final long id;
    private final long parentId;
    private final ContentHash root;

    public SnapshotHeader(long id, long parentId, ContentHash root) {
        this.id = id; this.parentId = parentId; this.root = root;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        w.writeInt(MAGIC);
        w.writeLong(id);
        w.writeLong(parentId);
        w.writeBytes(root.bytes());
        return w.toByteArray();
    }

    public static SnapshotHeader decode(byte[] data) throws Exception {
        BinaryReader r = BinaryReader.wrap(data);
        if (r.readInt() != MAGIC) throw new IllegalArgumentException("bad snapshot");
        return new SnapshotHeader(r.readLong(), r.readLong(), ContentHash.ofDigest(r.readBytes(32)));
    }

}
