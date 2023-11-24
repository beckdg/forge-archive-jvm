package dev.forgearchive.io;

import java.io.*;
public final class SeekableInput {
    private final RandomAccessFile file;

    public SeekableInput(File f) throws IOException {
        file = new RandomAccessFile(f, "r");
    }

    public void seek(long pos) throws IOException { file.seek(pos); }
    public long position() throws IOException { return file.getFilePointer(); }
    public int read(byte[] buf, int off, int len) throws IOException { return file.read(buf, off, len); }
    public void close() throws IOException { file.close(); }

}
