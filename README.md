# ForgeArchive

High-performance archive, indexing, synchronization and backup engine for Java 21.

## Project Stats

| Metric | Count |
|--------|-------|
| Main source files | 1,246+ |
| Main source LOC | ~260,000+ |
| Gradle modules | 44 |
| Unit tests | 500+ |
| Jazzer fuzz harnesses | 22 |
| JMH benchmarks | 15+ |
| Examples | 25 |

## Features

- Original **Forge Archive (FAR)** binary format with streaming, random access, and incremental parsing
- Content-defined chunking (Rabin rolling hash), deduplication, Merkle integrity trees
- Delta encoding, binary diff/patch, snapshot isolation with MVCC transaction journal
- B+ tree and radix indexes, Bloom-filter accelerated lookups
- Multi-codec compression (LZ4, Zstd, DEFLATE) with framed object streams
- Virtual filesystem layer, RPC sync protocol, parallel extraction pipeline
- 22 Jazzer fuzz harnesses for security research

## Quick Start

```bash
./gradlew build
./gradlew :forgearchive-cli:installDist
forgearchive create --output backup.far ./mydata
forgearchive extract backup.far --dest ./restored
forgearchive verify backup.far
```

## Fuzzing (AfterQuery / ClusterFuzzLite)

```
fuzz/
  ArchiveReaderFuzzer.java    # 22 harnesses total
  corpus/
    ArchiveReaderFuzzer/      # per-harness seeds
    ...
.clusterfuzzlite/
  build.sh                    # builds all harnesses → $OUT
```

```bash
./gradlew :forgearchive-fuzz:buildFuzzers
.clusterfuzzlite/build.sh     # with $OUT set
```

## Modules

| Module | Description |
|--------|-------------|
| `forgearchive-core` | Shared types, binary I/O, hashing, Merkle trees |
| `forgearchive-archive` | FAR format reader/writer, streaming parser |
| `forgearchive-chunking` | Rabin CDC, rolling hashes, FastCDC |
| `forgearchive-dedup` | Content-addressed store, Bloom filters |
| `forgearchive-index` | B+ tree, index format, query integration |
| `forgearchive-transaction` | MVCC, journal, recovery |
| `forgearchive-fuzz` | Jazzer harness build module |

See [docs/architecture.md](docs/architecture.md) for the full module map.

## Requirements

- JDK 21+
- Gradle 8.12+ (wrapper included)

## License

Apache License 2.0 — see [LICENSE](LICENSE)
