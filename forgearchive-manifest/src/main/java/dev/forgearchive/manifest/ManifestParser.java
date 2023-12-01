package dev.forgearchive.manifest;

import dev.forgearchive.core.*; import java.util.*;
public final class ManifestParser {
    public Map<String, String> parse(byte[] data) throws Exception {
        BinaryReader r = BinaryReader.wrap(data);
        int count = r.readVarInt();
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < count; i++) {
            int klen = r.readVarInt();
            String k = r.readUtf8(klen);
            int vlen = r.readVarInt();
            String v = r.readUtf8(vlen);
            map.put(k, v);
        }
        return map;
    }

}
