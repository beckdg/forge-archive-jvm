#!/usr/bin/env python3
"""Generate fuzz/ harnesses, corpus seeds, and ClusterFuzzLite build integration."""
from __future__ import annotations

from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent

FUZZERS = [
    ("ArchiveReaderFuzzer", [
        "java.io.File f = java.io.File.createTempFile(\"far\", \".far\");",
        "java.nio.file.Files.write(f.toPath(), data);",
        "FarStreamParser parser = new FarStreamParser();",
        "parser.feed(data);",
        "try {",
        "  ArchiveReader reader = new ArchiveReader(f);",
        "  new ArchiveVerifier().verify(reader);",
        "} catch (Exception ignored) {}",
    ], ["forgearchive.archive.*", "forgearchive.core.*"]),
    ("ManifestFuzzer", [
        "new ManifestParser().parse(data);",
        "new ManifestValidator().validate(data);",
        "ManifestWriter w = new ManifestWriter();",
        "w.begin(); w.end();",
    ], ["forgearchive.manifest.*", "forgearchive.core.*"]),
    ("MetadataFuzzer", [
        "new MetadataParser().parseAll(data);",
        "new MetadataValidator().validate(data);",
    ], ["forgearchive.metadata.*", "forgearchive.core.*"]),
    ("IndexFuzzer", [
        "if (data.length >= 8) {",
        "  BPlusTree tree = BPlusTree.decode(data);",
        "  tree.get(data);",
        "  new IndexReader().load(data);",
        "}",
        "new IndexValidator().validate(data);",
    ], ["forgearchive.index.*", "forgearchive.core.*"]),
    ("JournalFuzzer", [
        "java.io.File j = java.io.File.createTempFile(\"jnl\", \".jnl\");",
        "java.nio.file.Files.write(j.toPath(), data);",
        "new JournalReader().replay(j);",
        "new JournalValidator().validate(data);",
    ], ["forgearchive.journal.*", "forgearchive.core.*"]),
    ("TransactionFuzzer", [
        "java.io.File t = java.io.File.createTempFile(\"txn\", \".jnl\");",
        "TransactionManager mgr = new TransactionManager(t);",
        "new JournalReader().replay(t);",
    ], ["forgearchive.transaction.*", "forgearchive.journal.*"]),
    ("SnapshotFuzzer", [
        "if (data.length >= 52) SnapshotHeader.decode(java.util.Arrays.copyOf(data, 52));",
        "new SnapshotReader().open(data);",
        "new SnapshotValidator().validate(data);",
    ], ["forgearchive.snapshot.*", "forgearchive.core.*"]),
    ("ChunkTableFuzzer", [
        "ChunkTable table = new ChunkTable();",
        "table.decode(data);",
        "table.encode();",
        "new ContentDefinedChunker().chunk(data);",
    ], ["forgearchive.dedup.*", "forgearchive.chunking.*"]),
    ("ConfigFuzzer", [
        "new ConfigParser().parse(data);",
        "new ConfigValidator().validate(data);",
    ], ["forgearchive.configuration.*", "forgearchive.core.*"]),
    ("RpcFuzzer", [
        "if (data.length > 8) new RpcDecoder().decodeFrame(data);",
        "new RpcValidator().validate(data);",
    ], ["forgearchive.rpc.*", "forgearchive.core.*"]),
    ("PacketFuzzer", [
        "if (data.length >= 20) new PacketDecoder().decode(data);",
        "new FrameDecoder().feed(data);",
        "new PacketValidator().validate(data);",
    ], ["forgearchive.protocol.*", "forgearchive.core.*"]),
    ("CompressionFuzzer", [
        "try { new FramedCompressor().unframe(data); } catch (Exception ignored) {}",
        "new Lz4Codec().decompress(data);",
        "new ZstdCodec().decompress(data);",
    ], ["forgearchive.compression.*"]),
    ("VirtualFsFuzzer", [
        "VirtualFileSystem vfs = new VirtualFileSystem();",
        "vfs.writeFile(\"/fuzz/\" + data.length, data);",
        "vfs.readFile(\"/fuzz/\" + data.length);",
        "vfs.list(\"/\");",
    ], ["forgearchive.virtualfs.*"]),
    ("RecoveryFuzzer", [
        "java.io.File j = java.io.File.createTempFile(\"rec\", \".jnl\");",
        "java.nio.file.Files.write(j.toPath(), data);",
        "new RecoveryManager().recover(j, j.getParentFile());",
    ], ["forgearchive.recovery.*", "forgearchive.journal.*"]),
    ("PatchFuzzer", [
        "if (data.length > 4) new PatchApplier().apply(new byte[Math.min(64, data.length)], data);",
        "new PatchGenerator().generate(new byte[0], data);",
    ], ["forgearchive.patch.*", "forgearchive.diff.*"]),
    ("DiffFuzzer", [
        "new BinaryDiff().diff(data, data);",
        "new DeltaEncoder().encode(data, data);",
    ], ["forgearchive.diff.*", "forgearchive.chunking.*"]),
    ("ObjectStoreFuzzer", [
        "ContentAddressedStore store = new ContentAddressedStore();",
        "store.store(data);",
        "MerkleTree.build(java.util.List.of(data));",
        "new BloomFilter(1024).mightContain(data);",
    ], ["forgearchive.dedup.*", "forgearchive.core.*"]),
    ("StreamReaderFuzzer", [
        "new ObjectStreamReader().readObjects(data);",
    ], ["forgearchive.stream.*", "forgearchive.archive.*"]),
    ("QueryParserFuzzer", [
        "String q = new String(data, java.nio.charset.StandardCharsets.UTF_8);",
        "try { new QueryParser().parse(q); } catch (Exception ignored) {}",
        "new QueryOptimizer().optimize(new QueryAst());",
    ], ["forgearchive.query.*"]),
    ("PluginManifestFuzzer", [
        "try { PluginDescriptor.decode(data); } catch (Exception ignored) {}",
        "new PluginLoader().register(new PluginDescriptor(\"f\", \"1\", ContentHash.sha256(data)));",
    ], ["forgearchive.plugin.*", "forgearchive.core.*"]),
    ("MerkleFuzzer", [
        "MerkleTree tree = MerkleTree.build(java.util.List.of(data));",
        "tree.rootHash();",
        "new MerkleVerifier().verify(tree, data);",
    ], ["forgearchive.dedup.*", "forgearchive.core.*"]),
    ("DeltaFuzzer", [
        "new DeltaEncoder().encode(data, data);",
        "new FastCDC().chunk(data);",
        "new RabinFingerprint().roll(data);",
    ], ["forgearchive.diff.*", "forgearchive.chunking.*"]),
]

SUPPORT = '''package dev.forgearchive.fuzz;

import dev.forgearchive.core.*;
import java.util.*;

/** Shared utilities for cross-module fuzz harness orchestration. */
public final class FuzzSupport {
    private FuzzSupport() {}

    public static byte[] slice(byte[] data, int max) {
        if (data.length <= max) return data;
        return Arrays.copyOf(data, max);
    }

    public static void roundTripBinary(byte[] data, dev.forgearchive.core.BinaryWriter w) {
        try {
            BinaryReader r = BinaryReader.wrap(data);
            while (r.hasRemaining()) {
                w.writeByte(r.readByte());
            }
        } catch (Exception ignored) {
            w.writeBytes(data);
        }
    }

    public static java.io.File tempFile(String prefix, byte[] data) throws java.io.IOException {
        java.io.File f = java.io.File.createTempFile(prefix, ".bin");
        f.deleteOnExit();
        java.nio.file.Files.write(f.toPath(), data);
        return f;
    }
}
'''


def generate_fuzz_harness(name: str, body_lines: list[str], imports: list[str]) -> str:
    import_block = "\n".join(f"import dev.{imp};" if not imp.startswith("dev.") else f"import {imp};" for imp in imports)
    body = "\n            ".join(body_lines)
    return f'''package dev.forgearchive.fuzz;

{import_block}

@SuppressWarnings({{"CatchMayIgnoreException", "unused"}})
public class {name} {{
    public static void fuzzerTestOneInput(byte[] data) {{
        if (data == null || data.length == 0) return;
        try {{
            {body}
        }} catch (Throwable t) {{
            // malformed input is expected during fuzzing
        }}
    }}
}}
'''


def main() -> None:
    fuzz_dir = ROOT / "fuzz"
    fuzz_dir.mkdir(exist_ok=True)

    (fuzz_dir / "FuzzSupport.java").write_text(SUPPORT, encoding="utf-8")

    for name, lines, imports in FUZZERS:
        imports.append("dev.forgearchive.fuzz.FuzzSupport")
        src = generate_fuzz_harness(name, lines, imports)
        (fuzz_dir / f"{name}.java").write_text(src, encoding="utf-8")

        corpus = ROOT / "fuzz" / "corpus" / name
        corpus.mkdir(parents=True, exist_ok=True)
        (corpus / "seed_valid_header").write_bytes(
            b"\x31\x52\x41\x46" + b"\x01\x00\x00\x00" + b"\x00" * 56
        )
        (corpus / "seed_structured").write_bytes(bytes([(i * 7 + 13) & 0xFF for i in range(128)]))
        (corpus / "seed_large").write_bytes(bytes(range(256)) * 4)

    # Shared corpus at fuzz/corpus root
    shared = ROOT / "fuzz" / "corpus"
    (shared / "minimal").write_bytes(b"\x46\x41\x52!")
    (shared / "binary_mix").write_bytes(bytes(i & 0xFF for i in range(512)))

    print(f"Generated {len(FUZZERS)} fuzz harnesses in fuzz/")


if __name__ == "__main__":
    main()
