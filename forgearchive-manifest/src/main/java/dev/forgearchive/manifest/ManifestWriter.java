package dev.forgearchive.manifest;

import dev.forgearchive.core.*; import java.util.*;
public final class ManifestWriter {
    private final java.util.Map<String, String> entries = new java.util.LinkedHashMap<>();

    public ManifestWriter begin() {
        entries.clear();
        return this;
    }

    public ManifestWriter entry(String key, String value) {
        entries.put(key, value);
        return this;
    }

    public byte[] end() {
        return write(entries);
    }

    public byte[] write(Map<String, String> entries) {
        BinaryWriter w = new BinaryWriter();
        w.writeVarInt(entries.size());
        for (Map.Entry<String, String> e : entries.entrySet()) {
            byte[] k = e.getKey().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            byte[] v = e.getValue().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            w.writeVarInt(k.length);
            w.writeBytes(k);
            w.writeVarInt(v.length);
            w.writeBytes(v);
        }
        return w.toByteArray();
    }

}
