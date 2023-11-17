package dev.forgearchive.index;

import dev.forgearchive.archive.*;
import dev.forgearchive.core.*;

public final class IndexPipeline {

    public byte[] buildIndex(ArchiveReader reader) throws Exception {
        BPlusTree tree = new BPlusTree();
        for (FarEntry entry : reader.entries()) {
            tree.put(entry.path(), entry.hash().bytes());
        }
        return tree.encode();
    }

    public void query(byte[] indexBytes, byte[] keyPrefix) throws ForgeFormatException {
        BPlusTree tree = BPlusTree.decode(indexBytes);
        byte[] end = keyPrefix.clone();
        if (end.length > 0) {
            end[end.length - 1]++;
        }
        tree.range(keyPrefix, end);
    }
}
