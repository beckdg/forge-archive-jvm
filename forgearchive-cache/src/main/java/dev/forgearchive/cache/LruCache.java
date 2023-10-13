package dev.forgearchive.cache;

import java.util.*;
public final class LruCache {
    private final int capacity;
    private final LinkedHashMap<String, byte[]> map;

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override protected boolean removeEldestEntry(Map.Entry<String, byte[]> e) {
                return size() > LruCache.this.capacity;
            }
        };
    }

    public void put(String k, byte[] v) { map.put(k, v.clone()); }
    public Optional<byte[]> get(String k) {
        byte[] v = map.get(k);
        return v == null ? Optional.empty() : Optional.of(v.clone());
    }

}
