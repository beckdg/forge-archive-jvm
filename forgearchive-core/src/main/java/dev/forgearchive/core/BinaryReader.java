package dev.forgearchive.core;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class BinaryReader {
    private final ByteBuffer buffer;
    private final ByteOrder order;

    public BinaryReader(byte[] data) {
        this(ByteBuffer.wrap(Objects.requireNonNull(data)), ByteOrder.LITTLE_ENDIAN);
    }

    public BinaryReader(ByteBuffer buffer, ByteOrder order) {
        this.buffer = Objects.requireNonNull(buffer).order(order);
        this.order = order;
    }

    public ByteOrder order() { return order; }
    public int position() { return buffer.position(); }
    public int remaining() { return buffer.remaining(); }

    public boolean hasRemaining() { return buffer.hasRemaining(); }

    public void seek(int pos) {
        buffer.position(pos);
    }

    public byte readByte() throws EOFException {
        if (!buffer.hasRemaining()) throw new EOFException("no byte");
        return buffer.get();
    }

    public int readUnsignedByte() throws EOFException {
        return Byte.toUnsignedInt(readByte());
    }

    public short readShort() throws EOFException {
        require(2);
        return buffer.getShort();
    }

    public int readInt() throws EOFException {
        require(4);
        return buffer.getInt();
    }

    public long readLong() throws EOFException {
        require(8);
        return buffer.getLong();
    }

    public byte[] readBytes(int len) throws EOFException {
        require(len);
        byte[] out = new byte[len];
        buffer.get(out);
        return out;
    }

    public String readUtf8(int len) throws EOFException {
        return new String(readBytes(len), StandardCharsets.UTF_8);
    }

    public byte[] readRemaining() {
        byte[] out = new byte[buffer.remaining()];
        buffer.get(out);
        return out;
    }

    public int readVarInt() throws EOFException, ForgeFormatException {
        return VarInt.readUnsigned(this);
    }

    public long readVarLong() throws EOFException, ForgeFormatException {
        return VarInt.readUnsignedLong(this);
    }

    private void require(int n) throws EOFException {
        if (buffer.remaining() < n) throw new EOFException("need " + n);
    }

    public static BinaryReader wrap(byte[] data) {
        return new BinaryReader(data);
    }
}
