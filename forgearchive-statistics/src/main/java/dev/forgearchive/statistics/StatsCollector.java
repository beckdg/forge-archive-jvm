package dev.forgearchive.statistics;

import java.util.concurrent.atomic.*;
public final class StatsCollector {
    private final LongAdder bytesRead = new LongAdder();
    private final LongAdder bytesWritten = new LongAdder();
    private final LongAdder operations = new LongAdder();

    public void recordRead(long n) { bytesRead.add(n); operations.increment(); }
    public void recordWrite(long n) { bytesWritten.add(n); operations.increment(); }

    public long bytesRead() { return bytesRead.sum(); }
    public long bytesWritten() { return bytesWritten.sum(); }
    public long operations() { return operations.sum(); }

}
