package dev.forgearchive.journal;

import dev.forgearchive.core.*; import java.io.*; import java.util.*;
public final class JournalReader {
    public List<JournalRecord> replay(File file) throws Exception {
        List<JournalRecord> records = new ArrayList<>();
        byte[] data = new FileInputStream(file).readAllBytes();
        BinaryReader r = BinaryReader.wrap(data);
        while (r.remaining() > 0) {
            int len = r.readVarInt();
            byte[] rec = r.readBytes(len);
            records.add(JournalRecord.decode(BinaryReader.wrap(rec)));
        }
        return records;
    }

}
