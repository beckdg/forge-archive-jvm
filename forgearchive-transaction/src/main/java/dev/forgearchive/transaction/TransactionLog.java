package dev.forgearchive.transaction;

import dev.forgearchive.journal.*; import java.util.*;
public final class TransactionLog {
    private final List<JournalRecord> pending = new ArrayList<>();
    private long txId;

    public void begin(long id) { txId = id; pending.clear(); }
    public void stage(JournalRecord r) { pending.add(r); }
    public List<JournalRecord> commit() {
        List<JournalRecord> copy = new ArrayList<>(pending);
        pending.clear();
        return copy;
    }
    public void rollback() { pending.clear(); }
    public long txId() { return txId; }

}
