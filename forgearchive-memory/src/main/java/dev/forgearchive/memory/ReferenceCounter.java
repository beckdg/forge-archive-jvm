package dev.forgearchive.memory;

import java.util.concurrent.atomic.AtomicInteger;
public final class ReferenceCounter {
    private final AtomicInteger count = new AtomicInteger(1);

    public int retain() { return count.incrementAndGet(); }
    public int release() { return count.decrementAndGet(); }
    public int get() { return count.get(); }

}
