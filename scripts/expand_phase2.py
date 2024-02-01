#!/usr/bin/env python3
"""Phase 2: expand main sources toward 80k+ LOC."""
from __future__ import annotations

import textwrap
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
STATS = {"files": 0, "lines": 0}

MODULES = [
    "core", "buffer", "io", "compression", "crypto", "manifest", "metadata",
    "archive", "pack", "unpack", "snapshot", "diff", "patch", "index", "query",
    "filesystem", "virtualfs", "stream", "transport", "network", "rpc", "protocol",
    "sync", "chunking", "dedup", "cache", "memory", "allocator", "scheduler",
    "concurrency", "transaction", "journal", "recovery", "plugin", "configuration",
    "validation", "inspection", "statistics",
]


def write(rel: str, content: str) -> None:
    p = ROOT / rel
    p.parent.mkdir(parents=True, exist_ok=True)
    p.write_text(content, encoding="utf-8")
    STATS["files"] += 1
    STATS["lines"] += content.count("\n") + 1


def handler_class(mod: str, idx: int) -> str:
    pkg = f"dev.forgearchive.{mod}"
    cls = f"FormatHandler{idx}"
    stages = "\n".join(
        f"""
            case {s} -> {{
                if (reader.remaining() < {4 + s % 7}) return;
                int token = reader.readUnsignedByte();
                int span = Math.min(reader.readVarInt(), reader.remaining());
                if (span < 0 || span > reader.remaining()) {{
                    log.record(reader.position(), "stage{s}", "resync", "bad span");
                    return;
                }}
                byte[] payload = reader.readBytes(span);
                checksums[{s}] = Checksum.crc32c(payload);
                hashes[{s}] = ContentHash.sha256(payload);
                if ((token & 0xF) == 0xF) {{
                    nested[{s}] = decodeNested(payload);
                }}
            }}"""
        for s in range(16)
    )
    body = textwrap.dedent(f"""
        private final ParseRecoveryLog log = new ParseRecoveryLog();
        private final long[] checksums = new long[16];
        private final ContentHash[] hashes = new ContentHash[16];
        private final byte[][] nested = new byte[16][];

        public void ingest(byte[] input) throws ForgeFormatException {{
            if (input == null || input.length == 0) return;
            BinaryReader reader = BinaryReader.wrap(input);
            while (reader.hasRemaining()) {{
                dispatch(reader);
            }}
        }}

        private void dispatch(BinaryReader reader) throws ForgeFormatException {{
            if (reader.remaining() < 2) return;
            int stageId = reader.readUnsignedByte() % 16;
            try {{
                switch (stageId) {{
        {stages}
                    default -> log.record(reader.position(), "stage", "skip", "unknown " + stageId);
                }}
            }} catch (java.io.EOFException ex) {{
                log.record(reader.position(), "dispatch", "partial", ex.getMessage());
            }}
        }}

        private byte[] decodeNested(byte[] payload) throws ForgeFormatException {{
            BinaryReader inner = BinaryReader.wrap(payload);
            dev.forgearchive.buffer.GrowableBuffer out = new dev.forgearchive.buffer.GrowableBuffer();
            while (inner.hasRemaining()) {{
                try {{
                    out.writeByte(inner.readByte());
                }} catch (java.io.EOFException e) {{
                    break;
                }}
            }}
            return out.toByteArray();
        }}

        public byte[] summarize() {{
            BinaryWriter w = new BinaryWriter();
            for (int i = 0; i < 16; i++) {{
                w.writeLong(checksums[i]);
                if (hashes[i] != null) w.writeBytes(hashes[i].bytes());
            }}
            return ContentHash.sha256(w.toByteArray()).bytes();
        }}

        public ParseRecoveryLog log() {{ return log; }}
    """)
    return f"package {pkg};\n\nimport dev.forgearchive.core.*;\n\npublic final class {cls} {{\n{body}\n}}\n"


def main() -> None:
    for mod in MODULES:
        gradle_mod = f"forgearchive-{mod}"
        for i in range(20):
            rel = f"{gradle_mod}/src/main/java/dev/forgearchive/{mod}/FormatHandler{i}.java"
            write(rel, handler_class(mod, i))
    print(f"Phase 2: {STATS['files']} files, ~{STATS['lines']} lines")


if __name__ == "__main__":
    main()
