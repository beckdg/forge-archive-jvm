package dev.forgearchive.rpc;
import dev.forgearchive.core.*;
import java.util.*;

public final class RpcValidator {

public enum ValidationResult { VALID, INVALID, PARTIAL, RECOVERED }

private final ParseRecoveryLog recovery = new ParseRecoveryLog();

public List<String> validate(byte[] data) throws ForgeFormatException {
    List<String> errors = new ArrayList<>();
    if (data == null || data.length == 0) {
        errors.add("empty RPC input");
        return errors;
    }
    BinaryReader reader = BinaryReader.wrap(data);
    ValidationResult[] results = new ValidationResult[12];
        results[0] = stage0(reader, errors);
results[1] = stage1(reader, errors);
results[2] = stage2(reader, errors);
results[3] = stage3(reader, errors);
results[4] = stage4(reader, errors);
results[5] = stage5(reader, errors);
results[6] = stage6(reader, errors);
results[7] = stage7(reader, errors);
results[8] = stage8(reader, errors);
results[9] = stage9(reader, errors);
results[10] = stage10(reader, errors);
results[11] = stage11(reader, errors);
    for (int i = 0; i < results.length; i++) {
        if (results[i] == ValidationResult.INVALID) {
            recovery.record(reader.position(), "stage" + i, "fail", "invalid");
        }
    }
    return errors;
}

public ParseRecoveryLog recoveryLog() { return recovery; }

private ValidationResult stage0(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 4) {
            errors.add("RPC stage 0: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(8, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 0: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 3; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 0 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 0: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 0: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage1(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 5) {
            errors.add("RPC stage 1: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(9, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 1: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 4; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 1 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 1: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 1: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage2(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 6) {
            errors.add("RPC stage 2: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(10, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 2: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 5; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 2 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 2: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 2: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage3(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 7) {
            errors.add("RPC stage 3: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(11, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 3: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 6; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 3 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 3: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 3: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage4(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 8) {
            errors.add("RPC stage 4: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(12, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 4: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 7; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 4 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 4: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 4: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage5(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 9) {
            errors.add("RPC stage 5: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(13, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 5: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 3; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 5 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 5: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 5: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage6(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 10) {
            errors.add("RPC stage 6: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(14, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 6: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 4; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 6 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 6: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 6: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage7(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 11) {
            errors.add("RPC stage 7: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(15, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 7: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 5; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 7 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 7: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 7: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage8(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 12) {
            errors.add("RPC stage 8: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(16, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 8: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 6; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 8 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 8: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 8: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage9(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 13) {
            errors.add("RPC stage 9: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(17, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 9: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 7; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 9 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 9: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 9: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage10(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 14) {
            errors.add("RPC stage 10: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(18, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 10: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 3; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 10 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 10: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 10: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

private ValidationResult stage11(BinaryReader reader, List<String> errors) {
    long start = reader.position();
    try {
        if (reader.remaining() < 15) {
            errors.add("RPC stage 11: insufficient data at " + start);
            return ValidationResult.PARTIAL;
        }
        int marker = reader.readInt();
        long checksum = Checksum.crc32c(reader.readBytes(Math.min(19, reader.remaining())));
        if (marker == 0 && checksum == 0) {
            errors.add("RPC stage 11: null marker at " + start);
            return ValidationResult.INVALID;
        }
        for (int j = 0; j < 4; j++) {
            if (!reader.hasRemaining()) break;
            int tag = reader.readUnsignedByte();
            int len = Math.min(reader.readVarInt(), reader.remaining());
            if (len < 0 || len > reader.remaining()) {
                errors.add("RPC stage 11 tag " + tag + ": bad length at " + reader.position());
                return ValidationResult.RECOVERED;
            }
            byte[] field = reader.readBytes(len);
            if (field.length > 0 && field[0] == (byte) 0xFF) {
                errors.add("RPC stage 11: reserved tag " + tag);
            }
        }
        return ValidationResult.VALID;
    } catch (ForgeFormatException | java.io.EOFException ex) {
        errors.add("RPC stage 11: " + ex.getMessage());
        return ValidationResult.RECOVERED;
    }
}

}
