package dev.forgearchive.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public final class BinaryWriter {
    private byte[] buf;
    private int pos;
    private ByteOrder order = ByteOrder.LITTLE_ENDIAN;

    public BinaryWriter() { this(64); }
    public BinaryWriter(int cap) { buf = new byte[cap]; pos = 0; }

    public BinaryWriter order(ByteOrder o) { order = Objects.requireNonNull(o); return this; }
    public int size() { return pos; }

    public void reset() { pos = 0; }

    public void writeByte(byte v) { ensure(1); buf[pos++] = v; }
    public void writeShort(short v) { ensure(2); ByteBuffer.wrap(buf, pos, 2).order(order).putShort(v); pos += 2; }
    public void writeInt(int v) { ensure(4); ByteBuffer.wrap(buf, pos, 4).order(order).putInt(v); pos += 4; }
    public void writeLong(long v) { ensure(8); ByteBuffer.wrap(buf, pos, 8).order(order).putLong(v); pos += 8; }

    public void writeBytes(byte[] data) {
        Objects.requireNonNull(data);
        ensure(data.length);
        System.arraycopy(data, 0, buf, pos, data.length);
        pos += data.length;
    }

    public void writeUtf8(String s) {
        writeBytes(s.getBytes(StandardCharsets.UTF_8));
    }

    public void writeVarInt(int v) { pos = VarInt.writeUnsigned(buf, pos, v); ensure(0); }

    public byte[] toByteArray() { return Arrays.copyOf(buf, pos); }

    private void ensure(int extra) {
        int need = pos + extra;
        if (need <= buf.length) return;
        int n = Math.max(buf.length * 2, need);
        buf = Arrays.copyOf(buf, n);
    }
}
