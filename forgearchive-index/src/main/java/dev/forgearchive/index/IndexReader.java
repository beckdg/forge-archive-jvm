package dev.forgearchive.index;

import dev.forgearchive.core.*; import java.util.Arrays;
public final class IndexReader {
    public BPlusTree load(byte[] data) throws Exception {
        IndexHeader hdr = IndexHeader.decode(Arrays.copyOf(data, 48));
        BinaryReader r = BinaryReader.wrap(data);
        r.seek(48);
        BPlusTree tree = new BPlusTree(32);
        for (long i = 0; i < hdr.entryCount(); i++) {
            int klen = r.readVarInt();
            String k = r.readUtf8(klen);
            int vlen = r.readVarInt();
            tree.put(k, r.readBytes(vlen));
        }
        return tree;
    }

}
