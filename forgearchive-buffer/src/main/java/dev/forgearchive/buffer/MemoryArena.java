package dev.forgearchive.buffer;


public final class MemoryArena {
    private final byte[] block;
    private int offset;

    public MemoryArena(int size) { block = new byte[size]; }

    public ByteSlice allocate(int size) {
        if (offset + size > block.length) throw new OutOfMemoryError("arena full");
        ByteSlice slice = new ByteSlice(block, offset, size);
        offset += size;
        return slice;
    }

    public void reset() { offset = 0; }
    public int used() { return offset; }

}
