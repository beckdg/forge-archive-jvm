package dev.forgearchive.unpack;


import dev.forgearchive.archive.*;
import dev.forgearchive.scheduler.WorkStealingScheduler;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public final class ParallelExtractor {
    public void extract(File archive, File destDir, int parallelism) throws Exception {
        ArchiveReader reader = new ArchiveReader(archive);
        WorkStealingScheduler sched = new WorkStealingScheduler(parallelism);
        List<Future<?>> futures = new ArrayList<>();
        for (FarEntry e : reader.entries()) {
            futures.add(sched.submit(() -> {
                File out = new File(destDir, e.path());
                out.getParentFile().mkdirs();
                try (FileOutputStream fos = new FileOutputStream(out)) {
                    fos.write(reader.readEntry(e));
                }
                return null;
            }));
        }
        for (Future<?> f : futures) f.get();
        sched.shutdown();
    }

}
