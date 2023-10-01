package dev.forgearchive.allocator;

import dev.forgearchive.memory.MemoryPool;
public final class ArenaAllocator {
    private final MemoryPool pool;

    public ArenaAllocator(int size) { pool = new MemoryPool(size); }

    public byte[] allocate(int n) { return pool.alloc(n); }
    public void reset() { pool.reset(); }

}
