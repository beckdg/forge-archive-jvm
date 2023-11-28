package dev.forgearchive.journal;

import dev.forgearchive.core.BinaryWriter; import java.io.*;
public final class JournalWriter {
    private final File file;
    private long seq;

    public JournalWriter(File file) { this.file = file; }

    public synchronized void append(JournalRecord record) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            byte[] enc = record.encode();
            BinaryWriter w = new BinaryWriter();
            w.writeVarInt(enc.length);
            w.writeBytes(enc);
            fos.write(w.toByteArray());
            seq++;
        }
    }

}
