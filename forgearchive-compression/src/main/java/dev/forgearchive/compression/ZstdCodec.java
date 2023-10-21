package dev.forgearchive.compression;

import com.github.luben.zstd.*;
public final class ZstdCodec {
    public byte[] compress(byte[] input) { return Zstd.compress(input); }
    public byte[] decompress(byte[] input) {
        long size = Zstd.decompressedSize(input);
        if (size < 0) throw new IllegalArgumentException("unknown size");
        return Zstd.decompress(input, (int) size);
    }

}
