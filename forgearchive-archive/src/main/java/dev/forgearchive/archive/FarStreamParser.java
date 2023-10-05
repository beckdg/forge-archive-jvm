package dev.forgearchive.archive;
import dev.forgearchive.core.*;
import java.io.File;
import java.util.*;

public final class FarStreamParser {

public enum Stage { HEADER, MANIFEST_REF, ENTRY_TABLE, ENTRY_PAYLOAD, FOOTER, COMPLETE, FAILED }

private Stage stage = Stage.HEADER;
private final ParseRecoveryLog recovery = new ParseRecoveryLog();
private FarHeader header;
private int declaredEntries;
private int parsedEntries;
private long streamOffset;
private final List<FarEntry> entries = new ArrayList<>();
private ContentHash manifestHash;
private long footerCrc;

public Stage stage() { return stage; }
public ParseRecoveryLog recoveryLog() { return recovery; }
public List<FarEntry> entries() { return Collections.unmodifiableList(entries); }
public FarHeader header() { return header; }

public void reset() {
    stage = Stage.HEADER;
    recovery.clear();
    header = null;
    declaredEntries = 0;
    parsedEntries = 0;
    streamOffset = 0;
    entries.clear();
    manifestHash = null;
    footerCrc = 0;
}

public void feed(byte[] chunk) throws ForgeFormatException {
    Objects.requireNonNull(chunk);
    BinaryReader reader = BinaryReader.wrap(chunk);
    while (reader.remaining() > 0 && stage != Stage.COMPLETE && stage != Stage.FAILED) {
        feedAt(reader);
    }
}

private void feedAt(BinaryReader reader) throws ForgeFormatException {
    try {
        switch (stage) {
            case HEADER -> parseHeader(reader);
            case MANIFEST_REF -> parseManifestRef(reader);
            case ENTRY_TABLE -> parseEntryTable(reader);
            case ENTRY_PAYLOAD -> parseEntryPayload(reader);
            case FOOTER -> parseFooter(reader);
            default -> { }
        }
    } catch (java.io.EOFException e) {
        return;
    }
}

private void parseHeader(BinaryReader r) throws java.io.EOFException, ForgeFormatException {
    if (r.remaining() < 64) return;
    byte[] hdr = r.readBytes(64);
    streamOffset += 64;
    try {
        header = FarHeader.decode(hdr);
    } catch (ForgeFormatException ex) {
        recovery.record(streamOffset - 64, "header", "resync", ex.getMessage());
        stage = Stage.FAILED;
        return;
    } catch (Exception ex) {
        recovery.record(streamOffset - 64, "header", "resync", ex.getMessage());
        stage = Stage.FAILED;
        return;
    }
    if (header.magic() != ForgeVersions.FAR_MAGIC) {
        recovery.record(streamOffset - 64, "magic", "resync", "bad magic");
        stage = Stage.FAILED;
        return;
    }
    stage = Stage.MANIFEST_REF;
}

private void parseManifestRef(BinaryReader r) throws java.io.EOFException, ForgeFormatException {
    if (r.remaining() < 32) return;
    manifestHash = ContentHash.ofDigest(r.readBytes(32));
    streamOffset += 32;
    if (r.remaining() < 1) return;
    declaredEntries = r.readVarInt();
    streamOffset += VarInt.sizeUnsigned(declaredEntries);
    if (declaredEntries < 0 || declaredEntries > 10_000_000) {
        recovery.record(streamOffset, "entryCount", "clamp",
                "declared entries out of range: " + declaredEntries);
        declaredEntries = Math.max(0, Math.min(declaredEntries, 1_000_000));
    }
    stage = Stage.ENTRY_TABLE;
}

private void parseEntryTable(BinaryReader r) throws java.io.EOFException, ForgeFormatException {
    while (parsedEntries < declaredEntries && r.remaining() > 0) {
        try {
            FarEntry entry = FarEntry.decode(r);
            entries.add(entry);
            parsedEntries++;
            streamOffset += entry.encodedSize();
        } catch (ForgeFormatException ex) {
            recovery.record(streamOffset, "entry[" + parsedEntries + "]", "skip", ex.getMessage());
            if (r.remaining() > 0) {
                r.readByte();
                streamOffset++;
            }
            parsedEntries++;
        } catch (Exception ex) {
            recovery.record(streamOffset, "entry[" + parsedEntries + "]", "skip", ex.getMessage());
            parsedEntries++;
        }
    }
    if (parsedEntries >= declaredEntries) {
        stage = Stage.ENTRY_PAYLOAD;
    }
}

private void parseEntryPayload(BinaryReader r) {
    if (r.remaining() >= 8) {
        try {
            footerCrc = r.readLong();
            streamOffset += 8;
        } catch (java.io.EOFException ignored) {
            return;
        }
    }
    stage = Stage.FOOTER;
}

private void parseFooter(BinaryReader r) throws java.io.EOFException, ForgeFormatException {
    if (header == null) {
        stage = Stage.FAILED;
        return;
    }
    long expected = header.headerCrc();
    if (footerCrc != 0 && footerCrc != expected) {
        recovery.record(streamOffset, "footerCrc", "warn",
                "footer CRC mismatch expected=" + expected + " got=" + footerCrc);
    }
    stage = Stage.COMPLETE;
}

public ArchiveReader materialize(File file) throws Exception {
    if (stage != Stage.COMPLETE && stage != Stage.ENTRY_TABLE && entries.isEmpty()) {
        throw new ForgeFormatException("parser incomplete at stage " + stage);
    }
    return new ArchiveReader(file);
}

public boolean verifyManifestHash(byte[] manifestBytes) {
    if (manifestHash == null) return false;
    return manifestHash.equals(ContentHash.sha256(manifestBytes));
}

}
