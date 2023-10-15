package dev.forgearchive.chunking;


import dev.forgearchive.core.ContentHash;
import java.util.*;

public final class ContentDefinedChunker {
    private final int minSize;
    private final int avgSize;
    private final int maxSize;
    private final int mask;

    public ContentDefinedChunker(int minSize, int avgSize, int maxSize) {
        this.minSize = minSize;
        this.avgSize = avgSize;
        this.maxSize = maxSize;
        this.mask = avgSize - 1;
    }

    public ContentDefinedChunker() {
        this(4096, 8192, 65536);
    }

    public List<Chunk> chunk(byte[] data) {
        List<Chunk> chunks = new ArrayList<>();
        int start = 0;
        RollingHash rh = new RollingHash(48);
        for (int i = 0; i < data.length; i++) {
            rh.push(data[i]);
            int size = i - start + 1;
            if (size >= minSize && ((rh.hash() & mask) == 0 || size >= maxSize)) {
                byte[] slice = Arrays.copyOfRange(data, start, i + 1);
                chunks.add(new Chunk(ContentHash.sha256(slice), slice, start));
                start = i + 1;
                rh = new RollingHash(48);
            }
        }
        if (start < data.length) {
            byte[] slice = Arrays.copyOfRange(data, start, data.length);
            chunks.add(new Chunk(ContentHash.sha256(slice), slice, start));
        }
        return chunks;
    }

    public record Chunk(ContentHash hash, byte[] data, int offset) {}

}
