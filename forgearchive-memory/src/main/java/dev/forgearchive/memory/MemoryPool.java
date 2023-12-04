package dev.forgearchive.memory;


public final class MemoryPool {
    private final byte[] arena;
    private int offset;

    public MemoryPool(int size) { arena = new byte[size]; }

    public synchronized byte[] alloc(int size) {
        if (offset + size > arena.length) throw new OutOfMemoryError("pool exhausted");
        byte[] slice = new byte[size];
        System.arraycopy(arena, offset, slice, 0, size);
        offset += size;
        return slice;
    }

    public synchronized void reset() { offset = 0; }

}
