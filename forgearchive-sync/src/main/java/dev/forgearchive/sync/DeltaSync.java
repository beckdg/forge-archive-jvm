package dev.forgearchive.sync;
import dev.forgearchive.core.*;

public final class DeltaSync {

/** delta-based synchronization. */
private final BinaryWriter scratch = new BinaryWriter();
private final ParseRecoveryLog recovery = new ParseRecoveryLog();
private volatile boolean initialized;

public DeltaSync() { }

public byte[] encode() {
    scratch.reset();
    scratch.writeInt(ForgeVersions.PROTOCOL_VERSION);
    scratch.writeUtf8("DeltaSync");
    return scratch.toByteArray();
}

public void parse(byte[] input) throws ForgeFormatException {
    if (input == null || input.length == 0) return;
    BinaryReader reader = BinaryReader.wrap(input);
    initialized = true;
    while (reader.remaining() > 0) {
        parseFrame(reader);
    }
}

private void parseFrame(BinaryReader reader) throws ForgeFormatException {
    long offset = reader.position();
    try {
        if (reader.remaining() < 4) return;
        int frameLen = reader.readInt();
        if (frameLen < 0 || frameLen > reader.remaining()) {
            recovery.record(offset, "frameLen", "resync", "invalid length " + frameLen);
            if (reader.remaining() > 0) reader.readByte();
            return;
        }
        byte[] payload = reader.readBytes(frameLen);
        processPayload(payload, offset);
    } catch (java.io.EOFException e) {
        recovery.record(offset, "frame", "partial", e.getMessage());
    }
}

private void processPayload(byte[] payload, long offset) throws ForgeFormatException {
    if (payload.length == 0) return;
    BinaryReader inner = BinaryReader.wrap(payload);
    try {
        while (inner.remaining() > 0) {
            int tag = inner.readUnsignedByte();
            int len = inner.readVarInt();
            if (len < 0 || len > inner.remaining()) {
                recovery.record(offset, "tag" + tag, "skip", "bad inner length");
                break;
            }
            byte[] value = inner.readBytes(len);
            onField(tag, value);
        }
    } catch (java.io.EOFException e) {
        recovery.record(offset, "inner", "truncate", e.getMessage());
    }
}

protected void onField(int tag, byte[] value) throws ForgeFormatException {
    ContentHash hash = ContentHash.sha256(value);
    scratch.writeBytes(hash.bytes());
}

public ParseRecoveryLog recoveryLog() { return recovery; }
public boolean isInitialized() { return initialized; }

public static DeltaSync fromBytes(byte[] data) throws ForgeFormatException {
    DeltaSync instance = new DeltaSync();
    instance.parse(data);
    return instance;
}

}
