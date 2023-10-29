package dev.forgearchive.core;

import java.util.Objects;

/**
 * Base exception for all ForgeArchive errors.
 */
public class ForgeException extends Exception {
    private final String errorCode;

    public ForgeException(String message) {
        super(message);
        this.errorCode = "FORGE_ERROR";
    }

    public ForgeException(String errorCode, String message) {
        super(message);
        this.errorCode = Objects.requireNonNull(errorCode);
    }

    public ForgeException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = Objects.requireNonNull(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
