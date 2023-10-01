# ForgeArchive Developer Guide

## Build

Requires JDK 21. Gradle wrapper is included.

```bash
./gradlew build              # full build + tests
./gradlew compileJava        # compile all modules
./gradlew test               # run 500+ unit tests
./gradlew :forgearchive-fuzz:buildFuzzers
```

## Module Layout

Each `forgearchive-*` Gradle subproject maps to a logical layer:

- **core / buffer / io** — binary primitives, zero-copy buffers, seekable I/O
- **archive / manifest / metadata / journal** — FAR format and supporting structures
- **chunking / dedup / compression / crypto** — content-defined chunking, object store, codecs
- **index / query** — B+ tree indexes and query engine
- **pack / unpack / sync / recovery** — high-level archive operations
- **fuzz** — Jazzer harness sources (compiled via `forgearchive-fuzz`)

## Fuzzing

Harnesses live in `fuzz/` at the repository root. Each class exposes:

```java
public static void fuzzerTestOneInput(byte[] data) { ... }
```

Seed corpora: `fuzz/corpus/<HarnessName>/`

ClusterFuzzLite entry point: `.clusterfuzzlite/build.sh`

## Regenerating Sources

Bootstrap scripts in `scripts/`:

- `generate_forgearchive.py` — initial multi-module scaffold
- `expand_main_sources.py` — format parsers, validators, pipelines
- `expand_phase2.py` — multi-stage format handlers per module
- `generate_fuzz_harnesses.py` — fuzz harnesses + corpus seeds

## Code Quality

Checkstyle, ErrorProne, JaCoCo, and SpotBugs annotations are configured in the root `build.gradle.kts`.
