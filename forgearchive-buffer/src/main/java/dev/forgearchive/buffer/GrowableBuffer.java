package dev.forgearchive.buffer;

import dev.forgearchive.core.BinaryWriter;
public final class GrowableBuffer {
    private final BinaryWriter writer = new BinaryWriter();

    public void write(byte[] b) { writer.writeBytes(b); }
    public void writeByte(byte b) { writer.writeByte(b); }
    public byte[] toByteArray() { return writer.toByteArray(); }
    public int size() { return writer.size(); }

}
