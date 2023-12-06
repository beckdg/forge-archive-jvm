package dev.forgearchive.metadata;

import dev.forgearchive.core.*;
public final class MetadataRecord {
    private final String key;
    private final byte[] value;
    private final long timestamp;

    public MetadataRecord(String key, byte[] value, long timestamp) {
        this.key = key;
        this.value = value.clone();
        this.timestamp = timestamp;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        byte[] kb = key.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        w.writeVarInt(kb.length);
        w.writeBytes(kb);
        w.writeVarInt(value.length);
        w.writeBytes(value);
        w.writeLong(timestamp);
        return w.toByteArray();
    }

    public static MetadataRecord decode(BinaryReader r) throws Exception {
        int klen = r.readVarInt();
        String key = r.readUtf8(klen);
        int vlen = r.readVarInt();
        byte[] val = r.readBytes(vlen);
        long ts = r.readLong();
        return new MetadataRecord(key, val, ts);
    }

    public String key() { return key; }
    public byte[] value() { return value.clone(); }
    public long timestamp() { return timestamp; }

}
