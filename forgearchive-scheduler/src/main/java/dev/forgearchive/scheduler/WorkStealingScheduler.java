package dev.forgearchive.scheduler;


import java.util.concurrent.*;

public final class WorkStealingScheduler {
    private final ForkJoinPool pool;

    public WorkStealingScheduler(int parallelism) {
        pool = new ForkJoinPool(parallelism);
    }

    public <T> Future<T> submit(Callable<T> task) { return pool.submit(task); }
    public void shutdown() { pool.shutdown(); }

}
