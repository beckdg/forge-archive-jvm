package dev.forgearchive.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class MerkleTree {
    private final List<ContentHash> levels;

    private MerkleTree(List<ContentHash> levels) {
        this.levels = levels;
    }

    public static MerkleTree build(List<byte[]> leaves) {
        Objects.requireNonNull(leaves);
        if (leaves.isEmpty()) throw new IllegalArgumentException("empty leaves");
        List<ContentHash> current = new ArrayList<>();
        for (byte[] leaf : leaves) {
            current.add(ContentHash.sha256(leaf));
        }
        List<ContentHash> all = new ArrayList<>(current);
        while (current.size() > 1) {
            List<ContentHash> next = new ArrayList<>();
            for (int i = 0; i < current.size(); i += 2) {
                ContentHash left = current.get(i);
                ContentHash right = i + 1 < current.size() ? current.get(i + 1) : left;
                byte[] combined = new byte[64];
                System.arraycopy(left.bytes(), 0, combined, 0, 32);
                System.arraycopy(right.bytes(), 0, combined, 32, 32);
                next.add(ContentHash.sha256(combined));
            }
            current = next;
            all.addAll(current);
        }
        return new MerkleTree(all);
    }

    public ContentHash root() {
        return levels.get(levels.size() - 1);
    }

    public ContentHash rootHash() {
        return root();
    }

    public boolean verify(byte[] leaf, int index, List<ContentHash> proof) {
        ContentHash h = ContentHash.sha256(leaf);
        for (ContentHash sibling : proof) {
            byte[] combined = (index & 1) == 0
                ? concat(h.bytes(), sibling.bytes())
                : concat(sibling.bytes(), h.bytes());
            h = ContentHash.sha256(combined);
            index >>>= 1;
        }
        return h.equals(root());
    }

    private static byte[] concat(byte[] a, byte[] b) {
        byte[] out = new byte[a.length + b.length];
        System.arraycopy(a, 0, out, 0, a.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        return out;
    }
}
