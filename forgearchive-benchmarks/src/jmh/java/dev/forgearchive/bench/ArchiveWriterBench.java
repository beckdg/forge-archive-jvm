package dev.forgearchive.bench;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class ArchiveWriterBench {
    private byte[] data;

    @Setup
    public void setup() {
        data = new byte[4096];
        for (int i = 0; i < data.length; i++) data[i] = (byte) i;
    }

    @Benchmark
    public void run() throws Exception {
        // exercise ArchiveWriter
        dev.forgearchive.core.Checksum.crc32c(data);
        dev.forgearchive.core.ContentHash.sha256(data);
    }
}
