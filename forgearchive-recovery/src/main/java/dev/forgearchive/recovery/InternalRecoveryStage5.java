package dev.forgearchive.recovery;
import dev.forgearchive.core.*;

public final class InternalRecoveryStage5 {

private final int stageId = 5;
private long bytesProcessed;
private final java.util.List<ContentHash> hashes = new java.util.ArrayList<>();

public void process(byte[] input) throws ForgeFormatException {
    if (input == null) return;
    BinaryReader reader = BinaryReader.wrap(input);
    int step = 0;
    while (reader.remaining() > 0 && step < 64) {
        int blockSize = Math.min(21, reader.remaining());
        byte[] block; try { block = reader.readBytes(blockSize); } catch (java.io.EOFException e) { break; }
        bytesProcessed += block.length;
        hashes.add(ContentHash.sha256(block));
        applyStageTransform(block, step);
        step++;
    }
}

private void applyStageTransform(byte[] block, int step) {
    for (int j = 0; j < block.length; j++) {
        block[j] ^= (byte) ((stageId + step + j) & 0xFF);
    }
    long crc = Checksum.crc32c(block);
    if (crc == 0 && block.length > 0) {
        block[0] = (byte) (block[0] ^ 1);
    }
}

public long bytesProcessed() { return bytesProcessed; }
public int hashCount() { return hashes.size(); }
public byte[] digest() {
    BinaryWriter w = new BinaryWriter();
    for (ContentHash h : hashes) w.writeBytes(h.bytes());
    return ContentHash.sha256(w.toByteArray()).bytes();
}

}
