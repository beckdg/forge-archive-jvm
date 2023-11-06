package dev.forgearchive.dedup;


import dev.forgearchive.core.BinaryWriter;
import dev.forgearchive.core.ContentHash;
import java.util.*;

public final class ChunkTable {
    private final Map<ContentHash, byte[]> chunks = new HashMap<>();

    public boolean put(ContentHash hash, byte[] data) {
        return chunks.putIfAbsent(hash, data.clone()) == null;
    }

    public Optional<byte[]> get(ContentHash hash) {
        byte[] d = chunks.get(hash);
        return d == null ? Optional.empty() : Optional.of(d.clone());
    }

    public int size() { return chunks.size(); }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        w.writeVarInt(chunks.size());
        for (var e : chunks.entrySet()) {
            w.writeBytes(e.getKey().bytes());
            w.writeVarInt(e.getValue().length);
            w.writeBytes(e.getValue());
        }
        return w.toByteArray();
    }

    public void decode(byte[] data) throws Exception {
        chunks.clear();
        if (data == null || data.length == 0) return;
        dev.forgearchive.core.BinaryReader r = dev.forgearchive.core.BinaryReader.wrap(data);
        int count = r.readVarInt();
        for (int i = 0; i < count; i++) {
            ContentHash hash = ContentHash.ofDigest(r.readBytes(32));
            int len = r.readVarInt();
            chunks.put(hash, r.readBytes(len));
        }
    }

}
