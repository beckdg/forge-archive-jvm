package dev.forgearchive.concurrency;

import java.util.concurrent.locks.*;
public final class StripedLock {
    private final Lock[] locks;

    public StripedLock(int stripes) {
        locks = new Lock[stripes];
        for (int i = 0; i < stripes; i++) locks[i] = new ReentrantLock();
    }

    public Lock stripe(Object key) {
        int h = key.hashCode();
        return locks[(h & Integer.MAX_VALUE) % locks.length];
    }

}
