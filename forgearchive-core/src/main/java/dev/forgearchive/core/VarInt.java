package dev.forgearchive.core;

import java.io.EOFException;

public final class VarInt {
    private VarInt() {}

    public static int readUnsigned(BinaryReader r) throws EOFException, ForgeFormatException {
        int result = 0;
        int shift = 0;
        while (shift < 35) {
            int b = r.readUnsignedByte();
            result |= (b & 0x7F) << shift;
            if ((b & 0x80) == 0) return result;
            shift += 7;
        }
        throw new ForgeFormatException("VARINT_OVERFLOW", "varint too long");
    }

    public static long readUnsignedLong(BinaryReader r) throws EOFException, ForgeFormatException {
        long result = 0;
        int shift = 0;
        while (shift < 70) {
            int b = r.readUnsignedByte();
            result |= (long) (b & 0x7F) << shift;
            if ((b & 0x80) == 0) return result;
            shift += 7;
        }
        throw new ForgeFormatException("VARINT_OVERFLOW", "varlong too long");
    }

    public static int writeUnsigned(byte[] buf, int off, int value) {
        int v = value;
        while ((v & ~0x7F) != 0) {
            buf[off++] = (byte) ((v & 0x7F) | 0x80);
            v >>>= 7;
        }
        buf[off++] = (byte) v;
        return off;
    }

    public static int encodedLength(int value) {
        int len = 0;
        int v = value;
        do { len++; v >>>= 7; } while (v != 0);
        return len;
    }

    public static int sizeUnsigned(int value) {
        return encodedLength(value);
    }
}
