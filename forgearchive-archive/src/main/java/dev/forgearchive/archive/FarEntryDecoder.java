package dev.forgearchive.archive;
import dev.forgearchive.core.*;
import java.util.*;

public final class FarEntryDecoder {

private final BinaryReader reader;
private final ParseRecoveryLog log;
private int index;

public FarEntryDecoder(byte[] data) {
    this(data, new ParseRecoveryLog());
}

public FarEntryDecoder(byte[] data, ParseRecoveryLog log) {
    this.reader = BinaryReader.wrap(data);
    this.log = log;
}

public Optional<FarEntry> next() throws ForgeFormatException {
    if (!reader.hasRemaining()) return Optional.empty();
    long pos = reader.position();
    try {
        FarEntry e = FarEntry.decode(reader);
        index++;
        return Optional.of(e);
    } catch (ForgeFormatException ex) {
        log.record(pos, "entry[" + index + "]", "abort", ex.getMessage());
        throw ex;
    } catch (Exception ex) {
        if (ex instanceof java.io.EOFException) {
            return Optional.empty();
        }
        log.record(pos, "entry[" + index + "]", "abort", ex.getMessage());
        throw new ForgeFormatException(ex.getMessage(), pos, ex);
    }
}

public int parsedCount() { return index; }

}
