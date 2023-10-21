package dev.forgearchive.compression;

import net.jpountz.lz4.*;
public final class Lz4Codec {
    private final LZ4Factory factory = LZ4Factory.fastestInstance();
    private final LZ4Compressor compressor = factory.fastCompressor();
    private final LZ4FastDecompressor decompressor = factory.fastDecompressor();

    public byte[] compress(byte[] input) {
        int max = compressor.maxCompressedLength(input.length);
        byte[] out = new byte[max + 4];
        int len = compressor.compress(input, 0, input.length, out, 4, max);
        out[0] = (byte) (input.length);
        out[1] = (byte) (input.length >>> 8);
        out[2] = (byte) (input.length >>> 16);
        out[3] = (byte) (input.length >>> 24);
        byte[] result = new byte[len + 4];
        System.arraycopy(out, 0, result, 0, len + 4);
        return result;
    }

    public byte[] decompress(byte[] input) {
        int orig = (input[0] & 0xFF) | ((input[1] & 0xFF) << 8)
            | ((input[2] & 0xFF) << 16) | ((input[3] & 0xFF) << 24);
        byte[] out = new byte[orig];
        decompressor.decompress(input, 4, out, 0, orig);
        return out;
    }

}
