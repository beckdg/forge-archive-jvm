package dev.forgearchive.plugin;

import dev.forgearchive.core.*;
public final class PluginDescriptor {
    private final String id;
    private final String version;
    private final ContentHash hash;

    public PluginDescriptor(String id, String version, ContentHash hash) {
        this.id = id; this.version = version; this.hash = hash;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        w.writeUtf8(id);
        w.writeUtf8(version);
        w.writeBytes(hash.bytes());
        return w.toByteArray();
    }

    public static PluginDescriptor decode(byte[] data) throws Exception {
        BinaryReader r = BinaryReader.wrap(data);
        String id = readCString(r);
        String ver = readCString(r);
        return new PluginDescriptor(id, ver, ContentHash.ofDigest(r.readBytes(32)));
    }

    private static String readCString(BinaryReader r) {
        dev.forgearchive.buffer.GrowableBuffer buf = new dev.forgearchive.buffer.GrowableBuffer();
        byte b;
        try { while ((b = r.readByte()) != 0) buf.writeByte(b); }
        catch (java.io.EOFException e) { throw new RuntimeException(e); }
        return new String(buf.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);
    }

    public String id() { return id; }
    public String version() { return version; }
    public ContentHash hash() { return hash; }

}
