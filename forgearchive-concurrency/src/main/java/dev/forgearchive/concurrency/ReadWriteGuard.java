package dev.forgearchive.concurrency;

import java.util.concurrent.locks.*;
public final class ReadWriteGuard {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public <T> T read(java.util.function.Supplier<T> action) {
        lock.readLock().lock();
        try { return action.get(); }
        finally { lock.readLock().unlock(); }
    }

    public void write(Runnable action) {
        lock.writeLock().lock();
        try { action.run(); }
        finally { lock.writeLock().unlock(); }
    }

}
