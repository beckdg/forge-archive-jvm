package dev.forgearchive.configuration;

import dev.forgearchive.core.*; import java.util.*;
public final class ConfigParser {
    public Map<String, String> parse(byte[] data) throws Exception {
        BinaryReader r = BinaryReader.wrap(data);
        int pairs = r.readVarInt();
        Map<String, String> cfg = new LinkedHashMap<>();
        for (int i = 0; i < pairs; i++) {
            int klen = r.readVarInt();
            String k = r.readUtf8(klen);
            int vlen = r.readVarInt();
            cfg.put(k, r.readUtf8(vlen));
        }
        return cfg;
    }

    public byte[] write(Map<String, String> cfg) {
        BinaryWriter w = new BinaryWriter();
        w.writeVarInt(cfg.size());
        for (var e : cfg.entrySet()) {
            byte[] kb = e.getKey().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            byte[] vb = e.getValue().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            w.writeVarInt(kb.length); w.writeBytes(kb);
            w.writeVarInt(vb.length); w.writeBytes(vb);
        }
        return w.toByteArray();
    }

}
