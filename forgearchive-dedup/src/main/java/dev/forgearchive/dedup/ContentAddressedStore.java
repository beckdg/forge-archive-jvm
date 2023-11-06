package dev.forgearchive.dedup;


import dev.forgearchive.core.ContentHash;
import java.util.*;

public final class ContentAddressedStore {
    private final ChunkTable table = new ChunkTable();
    private final BloomFilter bloom = new BloomFilter(10000, 0.01);

    public ContentHash store(byte[] data) {
        ContentHash h = ContentHash.sha256(data);
        if (!bloom.mightContain(h.bytes())) {
            bloom.add(h.bytes());
            table.put(h, data);
        } else if (table.get(h).isEmpty()) {
            table.put(h, data);
        }
        return h;
    }

    public Optional<byte[]> load(ContentHash hash) { return table.get(hash); }

}
