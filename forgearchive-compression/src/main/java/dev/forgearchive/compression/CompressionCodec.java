package dev.forgearchive.compression;


public enum CompressionCodec {
    LZ4(1), ZSTD(2), DEFLATE(3);
    private final int id;
    CompressionCodec(int id) { this.id = id; }
    public int id() { return id; }
    public static CompressionCodec fromId(int id) {
        for (CompressionCodec c : values()) if (c.id == id) return c;
        throw new IllegalArgumentException("unknown codec " + id);
    }

}
