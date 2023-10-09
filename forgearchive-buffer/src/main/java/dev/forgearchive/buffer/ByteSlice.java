package dev.forgearchive.buffer;


public final class ByteSlice {
    private final byte[] data;
    private final int offset;
    private final int length;

    public ByteSlice(byte[] data, int offset, int length) {
        if (offset < 0 || length < 0 || offset + length > data.length)
            throw new IndexOutOfBoundsException();
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    public ByteSlice(byte[] data) { this(data, 0, data.length); }
    public int length() { return length; }
    public byte get(int i) { return data[offset + i]; }
    public byte[] copyBytes() {
        byte[] out = new byte[length];
        System.arraycopy(data, offset, out, 0, length);
        return out;
    }
    public ByteSlice slice(int off, int len) { return new ByteSlice(data, offset + off, len); }

}
