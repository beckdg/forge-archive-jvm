package dev.forgearchive.core;

/**
 * Recoverable parse warning accumulated during malformed-input recovery.
 */
public final class ParseRecoveryEvent {
    private final long offset;
    private final String field;
    private final String action;
    private final String detail;

    public ParseRecoveryEvent(long offset, String field, String action, String detail) {
        this.offset = offset;
        this.field = field;
        this.action = action;
        this.detail = detail;
    }

    public long getOffset() {
        return offset;
    }

    public String getField() {
        return field;
    }

    public String getAction() {
        return action;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return "ParseRecoveryEvent{offset=" + offset + ", field='" + field
                + "', action='" + action + "', detail='" + detail + "'}";
    }
}
