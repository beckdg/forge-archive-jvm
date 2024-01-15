package dev.forgearchive.transaction;


import dev.forgearchive.journal.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class TransactionManager {
    private final JournalWriter writer;
    private final AtomicLong versions = new AtomicLong(1);
    private final Map<String, byte[]> store = new ConcurrentHashMap<>();
    private final TransactionLog log = new TransactionLog();

    public TransactionManager(File journalFile) { writer = new JournalWriter(journalFile); }

    public MvccSnapshot snapshot() {
        Map<String, byte[]> copy = new HashMap<>();
        store.forEach((k, v) -> copy.put(k, v.clone()));
        return new MvccSnapshot(versions.get(), copy);
    }

    public void apply(JournalRecord r) throws IOException {
        switch (r.op()) {
            case APPEND -> store.put(r.target(), r.payload());
            case DELETE -> store.remove(r.target());
            case TRUNCATE -> store.clear();
        }
        writer.append(r);
        versions.incrementAndGet();
    }

}
