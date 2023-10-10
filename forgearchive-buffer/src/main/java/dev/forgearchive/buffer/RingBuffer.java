package dev.forgearchive.buffer;

import java.util.concurrent.atomic.AtomicLong;
public final class RingBuffer {
    private final byte[] buf;
    private final int capacity;
    private final AtomicLong writePos = new AtomicLong();
    private final AtomicLong readPos = new AtomicLong();

    public RingBuffer(int capacity) {
        this.capacity = capacity;
        this.buf = new byte[capacity];
    }

    public boolean offer(byte b) {
        long w = writePos.get();
        long r = readPos.get();
        if (w - r >= capacity) return false;
        buf[(int) (w % capacity)] = b;
        writePos.incrementAndGet();
        return true;
    }

    public Byte poll() {
        long r = readPos.get();
        long w = writePos.get();
        if (r >= w) return null;
        byte b = buf[(int) (r % capacity)];
        readPos.incrementAndGet();
        return b;
    }

    public int size() { return (int) (writePos.get() - readPos.get()); }

}
