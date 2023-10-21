package dev.forgearchive.compression;


import dev.forgearchive.core.BinaryWriter;
import dev.forgearchive.core.Checksum;

public final class FramedCompressor {
    public byte[] frame(CompressionCodec codec, byte[] payload) throws Exception {
        byte[] compressed;
        switch (codec) {
            case LZ4 -> compressed = new Lz4Codec().compress(payload);
            case ZSTD -> compressed = new ZstdCodec().compress(payload);
            case DEFLATE -> compressed = new DeflateCodec().compress(payload);
            default -> throw new IllegalStateException();
        }
        BinaryWriter w = new BinaryWriter();
        w.writeInt(0x46415243);
        w.writeByte((byte) codec.id());
        w.writeInt(payload.length);
        w.writeInt(compressed.length);
        w.writeLong(Checksum.crc32c(compressed));
        w.writeBytes(compressed);
        return w.toByteArray();
    }

    public byte[] unframe(byte[] framed) throws Exception {
        dev.forgearchive.core.BinaryReader r = dev.forgearchive.core.BinaryReader.wrap(framed);
        int magic = r.readInt();
        if (magic != 0x46415243) throw new dev.forgearchive.core.ForgeFormatException("BAD_MAGIC", "bad frame");
        CompressionCodec codec = CompressionCodec.fromId(r.readUnsignedByte());
        r.readInt();
        int clen = r.readInt();
        long crc = r.readLong();
        byte[] comp = r.readBytes(clen);
        if (Checksum.crc32c(comp) != crc) throw new dev.forgearchive.core.ForgeFormatException("CRC", "crc mismatch");
        return switch (codec) {
            case LZ4 -> new Lz4Codec().decompress(comp);
            case ZSTD -> new ZstdCodec().decompress(comp);
            case DEFLATE -> new DeflateCodec().decompress(comp);
        };
    }

}
