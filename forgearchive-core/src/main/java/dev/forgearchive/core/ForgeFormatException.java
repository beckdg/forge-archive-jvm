package dev.forgearchive.core;

/**
 * Non-recoverable format or protocol violation.
 */
public final class ForgeFormatException extends ForgeException {
    private final long offset;

    public ForgeFormatException(String message) {
        super("FORMAT_ERROR", message);
        this.offset = -1;
    }

    public ForgeFormatException(String errorCode, String message) {
        super(errorCode, message);
        this.offset = -1;
    }

    public ForgeFormatException(String message, long offset) {
        super("FORMAT_ERROR", message + " at offset " + offset);
        this.offset = offset;
    }

    public ForgeFormatException(String message, long offset, Throwable cause) {
        super("FORMAT_ERROR", message + " at offset " + offset, cause);
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }
}
