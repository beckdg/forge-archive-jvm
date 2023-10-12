package dev.forgearchive.cache;

import java.util.*;
public final class ArcCache {
    private final int capacity;
    private final LinkedHashMap<String, byte[]> t1 = new LinkedHashMap<>();
    private final LinkedHashMap<String, byte[]> t2 = new LinkedHashMap<>();
    private final LinkedHashMap<String, byte[]> b1 = new LinkedHashMap<>();
    private final LinkedHashMap<String, byte[]> b2 = new LinkedHashMap<>();

    public ArcCache(int capacity) { this.capacity = capacity; }

    public void put(String k, byte[] v) {
        if (t1.containsKey(k) || b1.containsKey(k)) {
            t1.put(k, v.clone());
            b1.remove(k);
        } else {
            t2.put(k, v.clone());
            b2.remove(k);
        }
        evict();
    }

    public Optional<byte[]> get(String k) {
        if (t1.containsKey(k)) return Optional.of(t1.get(k).clone());
        if (t2.containsKey(k)) return Optional.of(t2.get(k).clone());
        if (b1.containsKey(k)) { promoteGhost(b1, t2, k); return Optional.empty(); }
        if (b2.containsKey(k)) { promoteGhost(b2, t1, k); return Optional.empty(); }
        return Optional.empty();
    }

    private void promoteGhost(LinkedHashMap<String, byte[]> ghost,
                              LinkedHashMap<String, byte[]> target, String k) {
        ghost.remove(k);
        target.put(k, new byte[0]);
    }

    private void evict() {
        while (t1.size() + t2.size() > capacity && !t1.isEmpty()) {
            var it = t1.entrySet().iterator();
            var e = it.next();
            b1.put(e.getKey(), e.getValue());
            it.remove();
        }
    }

}
