package dev.forgearchive.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collects recovery events during incremental or resilient parsing.
 */
public final class ParseRecoveryLog {
    private final List<ParseRecoveryEvent> events = new ArrayList<>();
    private boolean strict;

    public ParseRecoveryLog() {
        this(false);
    }

    public ParseRecoveryLog(boolean strict) {
        this.strict = strict;
    }

    public void record(long offset, String field, String action, String detail)
            throws ForgeFormatException {
        if (strict) {
            throw new ForgeFormatException(
                    "strict mode: " + detail + " (" + field + ")", offset);
        }
        events.add(new ParseRecoveryEvent(offset, field, action, detail));
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public boolean isStrict() {
        return strict;
    }

    public List<ParseRecoveryEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public int size() {
        return events.size();
    }

    public void clear() {
        events.clear();
    }
}
