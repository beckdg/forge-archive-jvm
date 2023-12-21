package dev.forgearchive.protocol;

import dev.forgearchive.core.*;
public final class PacketHeader {
    public static final int MAGIC = 0x504B5400;
    private final int type;
    private final int length;
    private final long sequence;

    public PacketHeader(int type, int length, long sequence) {
        this.type = type; this.length = length; this.sequence = sequence;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        w.writeInt(MAGIC);
        w.writeInt(type);
        w.writeInt(length);
        w.writeLong(sequence);
        return w.toByteArray();
    }

    public static PacketHeader decode(byte[] data) throws Exception {
        BinaryReader r = BinaryReader.wrap(data);
        if (r.readInt() != MAGIC) throw new IllegalArgumentException("bad packet");
        return new PacketHeader(r.readInt(), r.readInt(), r.readLong());
    }

    public int type() { return type; }
    public int length() { return length; }
    public long sequence() { return sequence; }

}
