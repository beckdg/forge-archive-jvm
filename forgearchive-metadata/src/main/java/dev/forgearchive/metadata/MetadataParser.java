package dev.forgearchive.metadata;

import dev.forgearchive.core.*; import java.util.*;
public final class MetadataParser {
    public List<MetadataRecord> parseAll(byte[] blob) throws Exception {
        BinaryReader r = BinaryReader.wrap(blob);
        int n = r.readVarInt();
        List<MetadataRecord> out = new ArrayList<>();
        for (int i = 0; i < n; i++) out.add(MetadataRecord.decode(r));
        return out;
    }

}
