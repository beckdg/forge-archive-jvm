package dev.forgearchive.memory;

import dev.forgearchive.core.*;

public final class FormatHandler15 {

private final ParseRecoveryLog log = new ParseRecoveryLog();
private final long[] checksums = new long[16];
private final ContentHash[] hashes = new ContentHash[16];
private final byte[][] nested = new byte[16][];

public void ingest(byte[] input) throws ForgeFormatException {
    if (input == null || input.length == 0) return;
    BinaryReader reader = BinaryReader.wrap(input);
    while (reader.hasRemaining()) {
        dispatch(reader);
    }
}

private void dispatch(BinaryReader reader) throws ForgeFormatException {
    if (reader.remaining() < 2) return;
    int stageId;
    try {
        stageId = reader.readUnsignedByte() % 16;
    } catch (java.io.EOFException e) {
        return;
    }
    try {
        switch (stageId) {

    case 0 -> {
        if (reader.remaining() < 4) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage0", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[0] = Checksum.crc32c(payload);
        hashes[0] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[0] = decodeNested(payload);
        }
    }

    case 1 -> {
        if (reader.remaining() < 5) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage1", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[1] = Checksum.crc32c(payload);
        hashes[1] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[1] = decodeNested(payload);
        }
    }

    case 2 -> {
        if (reader.remaining() < 6) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage2", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[2] = Checksum.crc32c(payload);
        hashes[2] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[2] = decodeNested(payload);
        }
    }

    case 3 -> {
        if (reader.remaining() < 7) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage3", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[3] = Checksum.crc32c(payload);
        hashes[3] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[3] = decodeNested(payload);
        }
    }

    case 4 -> {
        if (reader.remaining() < 8) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage4", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[4] = Checksum.crc32c(payload);
        hashes[4] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[4] = decodeNested(payload);
        }
    }

    case 5 -> {
        if (reader.remaining() < 9) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage5", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[5] = Checksum.crc32c(payload);
        hashes[5] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[5] = decodeNested(payload);
        }
    }

    case 6 -> {
        if (reader.remaining() < 10) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage6", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[6] = Checksum.crc32c(payload);
        hashes[6] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[6] = decodeNested(payload);
        }
    }

    case 7 -> {
        if (reader.remaining() < 4) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage7", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[7] = Checksum.crc32c(payload);
        hashes[7] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[7] = decodeNested(payload);
        }
    }

    case 8 -> {
        if (reader.remaining() < 5) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage8", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[8] = Checksum.crc32c(payload);
        hashes[8] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[8] = decodeNested(payload);
        }
    }

    case 9 -> {
        if (reader.remaining() < 6) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage9", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[9] = Checksum.crc32c(payload);
        hashes[9] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[9] = decodeNested(payload);
        }
    }

    case 10 -> {
        if (reader.remaining() < 7) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage10", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[10] = Checksum.crc32c(payload);
        hashes[10] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[10] = decodeNested(payload);
        }
    }

    case 11 -> {
        if (reader.remaining() < 8) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage11", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[11] = Checksum.crc32c(payload);
        hashes[11] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[11] = decodeNested(payload);
        }
    }

    case 12 -> {
        if (reader.remaining() < 9) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage12", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[12] = Checksum.crc32c(payload);
        hashes[12] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[12] = decodeNested(payload);
        }
    }

    case 13 -> {
        if (reader.remaining() < 10) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage13", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[13] = Checksum.crc32c(payload);
        hashes[13] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[13] = decodeNested(payload);
        }
    }

    case 14 -> {
        if (reader.remaining() < 4) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage14", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[14] = Checksum.crc32c(payload);
        hashes[14] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[14] = decodeNested(payload);
        }
    }

    case 15 -> {
        if (reader.remaining() < 5) return;
        int token = reader.readUnsignedByte();
        int span = Math.min(reader.readVarInt(), reader.remaining());
        if (span < 0 || span > reader.remaining()) {
            log.record(reader.position(), "stage15", "resync", "bad span");
            return;
        }
        byte[] payload = reader.readBytes(span);
        checksums[15] = Checksum.crc32c(payload);
        hashes[15] = ContentHash.sha256(payload);
        if ((token & 0xF) == 0xF) {
            nested[15] = decodeNested(payload);
        }
    }
            default -> log.record(reader.position(), "stage", "skip", "unknown " + stageId);
        }
    } catch (java.io.EOFException ex) {
        log.record(reader.position(), "dispatch", "partial", ex.getMessage());
    }
}

private byte[] decodeNested(byte[] payload) throws ForgeFormatException {
    BinaryReader inner = BinaryReader.wrap(payload);
    BinaryWriter out = new BinaryWriter();
    while (inner.hasRemaining()) {
        try {
            out.writeByte(inner.readByte());
        } catch (java.io.EOFException e) {
            break;
        }
    }
    return out.toByteArray();
}

public byte[] summarize() {
    BinaryWriter w = new BinaryWriter();
    for (int i = 0; i < 16; i++) {
        w.writeLong(checksums[i]);
        if (hashes[i] != null) w.writeBytes(hashes[i].bytes());
    }
    return ContentHash.sha256(w.toByteArray()).bytes();
}

public ParseRecoveryLog log() { return log; }

}
