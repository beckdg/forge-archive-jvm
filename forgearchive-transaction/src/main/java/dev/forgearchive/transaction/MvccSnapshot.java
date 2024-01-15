package dev.forgearchive.transaction;

import java.util.*;
public final class MvccSnapshot {
    private final long version;
    private final Map<String, byte[]> state;

    public MvccSnapshot(long version, Map<String, byte[]> state) {
        this.version = version;
        this.state = Map.copyOf(state);
    }

    public long version() { return version; }
    public Optional<byte[]> get(String key) {
        return Optional.ofNullable(state.get(key)).map(b -> b.clone());
    }

}
