package dev.forgearchive.scheduler;

import java.util.concurrent.*;
public final class PriorityQueueScheduler {
    private final PriorityBlockingQueue<RunnableTask> queue = new PriorityBlockingQueue<>();
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public record RunnableTask(int priority, Runnable task) implements Comparable<RunnableTask> {
        public int compareTo(RunnableTask o) { return Integer.compare(o.priority, priority); }
    }

    public void schedule(int priority, Runnable r) {
        queue.offer(new RunnableTask(priority, r));
        exec.submit(() -> { RunnableTask t = queue.poll(); if (t != null) t.task().run(); });
    }

    public void shutdown() { exec.shutdown(); }

}
