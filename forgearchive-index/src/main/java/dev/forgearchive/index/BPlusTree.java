package dev.forgearchive.index;
import dev.forgearchive.core.*;
import java.util.*;
import java.util.Objects;

public final class BPlusTree {

private static final int ORDER = 32;
private Node root = new LeafNode();
private int size;

public BPlusTree() { }

public BPlusTree(int order) {
    // order parameter reserved for future tuning; internal ORDER is used
}

public int size() { return size; }

public void put(String key, byte[] value) {
    put(key.getBytes(java.nio.charset.StandardCharsets.UTF_8), value);
}

public void put(byte[] key, byte[] value) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);
    if (root.isFull()) {
        Node old = root;
        root = new InternalNode();
        ((InternalNode) root).children.add(old);
        splitChild(root, 0);
    }
    insertNonFull(root, key, value);
    size++;
}

public Optional<byte[]> get(byte[] key) {
    return findLeaf(root, key).get(key);
}

public void remove(byte[] key) {
    if (get(key).isEmpty()) return;
    removeFromNode(root, key);
    if (root instanceof InternalNode internal && internal.keys.isEmpty()) {
        root = internal.children.get(0);
    }
    size = Math.max(0, size - 1);
}

public List<Map.Entry<byte[], byte[]>> range(byte[] start, byte[] end) {
    List<Map.Entry<byte[], byte[]>> out = new ArrayList<>();
    LeafNode leaf = findLeaf(root, start);
    while (leaf != null) {
        for (int i = 0; i < leaf.keys.size(); i++) {
            byte[] k = leaf.keys.get(i);
            if (compare(k, start) >= 0 && compare(k, end) <= 0) {
                out.add(Map.entry(k, leaf.values.get(i)));
            }
        }
        leaf = leaf.next;
    }
    return out;
}

private void insertNonFull(Node node, byte[] key, byte[] value) {
    if (node instanceof LeafNode leaf) {
        int idx = Collections.binarySearch(leaf.keys, key, this::compare);
        if (idx < 0) idx = -idx - 1;
        leaf.keys.add(idx, key);
        leaf.values.add(idx, value);
        return;
    }
    InternalNode internal = (InternalNode) node;
    int i = Collections.binarySearch(internal.keys, key, this::compare);
    if (i < 0) i = -i - 1;
    if (internal.children.get(i).isFull()) {
        splitChild(internal, i);
        if (compare(key, internal.keys.get(i)) > 0) i++;
    }
    insertNonFull(internal.children.get(i), key, value);
}

private void splitChild(Node parent, int index) {
    if (parent instanceof InternalNode internal) {
        Node full = internal.children.get(index);
        int mid = ORDER / 2;
        if (full instanceof LeafNode leaf) {
            LeafNode right = new LeafNode();
            right.keys.addAll(leaf.keys.subList(mid, leaf.keys.size()));
            right.values.addAll(leaf.values.subList(mid, leaf.values.size()));
            leaf.keys.subList(mid, leaf.keys.size()).clear();
            leaf.values.subList(mid, leaf.values.size()).clear();
            right.next = leaf.next;
            leaf.next = right;
            internal.keys.add(index, right.keys.get(0));
            internal.children.add(index + 1, right);
        } else if (full instanceof InternalNode left) {
            InternalNode right = new InternalNode();
            right.keys.addAll(left.keys.subList(mid, left.keys.size()));
            left.keys.subList(mid, left.keys.size()).clear();
            right.children.addAll(left.children.subList(mid, left.children.size()));
            left.children.subList(mid, left.children.size()).clear();
            internal.keys.add(index, right.keys.get(0));
            right.keys.remove(0);
            internal.children.add(index + 1, right);
        }
    }
}

private void removeFromNode(Node node, byte[] key) {
    if (node instanceof LeafNode leaf) {
        int idx = Collections.binarySearch(leaf.keys, key, this::compare);
        if (idx >= 0) {
            leaf.keys.remove(idx);
            leaf.values.remove(idx);
        }
        return;
    }
    InternalNode internal = (InternalNode) node;
    int i = Collections.binarySearch(internal.keys, key, this::compare);
    if (i < 0) i = -i - 1;
    removeFromNode(internal.children.get(i), key);
}

private LeafNode findLeaf(Node node, byte[] key) {
    if (node instanceof LeafNode leaf) return leaf;
    InternalNode internal = (InternalNode) node;
    int i = Collections.binarySearch(internal.keys, key, this::compare);
    if (i < 0) i = -i - 1;
    return findLeaf(internal.children.get(i), key);
}

private int compare(byte[] a, byte[] b) {
    int len = Math.min(a.length, b.length);
    for (int i = 0; i < len; i++) {
        int d = Byte.compare(a[i], b[i]);
        if (d != 0) return d;
    }
    return Integer.compare(a.length, b.length);
}

public byte[] encode() {
    BinaryWriter w = new BinaryWriter();
    w.writeInt(ForgeVersions.INDEX_MAGIC);
    w.writeInt(size);
    encodeNode(w, root);
    return w.toByteArray();
}

private void encodeNode(BinaryWriter w, Node node) {
    if (node instanceof LeafNode leaf) {
        w.writeByte((byte) 0);
        w.writeVarInt(leaf.keys.size());
        for (int i = 0; i < leaf.keys.size(); i++) {
            w.writeVarInt(leaf.keys.get(i).length);
            w.writeBytes(leaf.keys.get(i));
            w.writeVarInt(leaf.values.get(i).length);
            w.writeBytes(leaf.values.get(i));
        }
    } else {
        InternalNode internal = (InternalNode) node;
        w.writeByte((byte) 1);
        w.writeVarInt(internal.keys.size());
        for (byte[] k : internal.keys) {
            w.writeVarInt(k.length);
            w.writeBytes(k);
        }
        w.writeVarInt(internal.children.size());
        for (Node c : internal.children) encodeNode(w, c);
    }
}

public static BPlusTree decode(byte[] data) throws ForgeFormatException {
    BinaryReader r = BinaryReader.wrap(data);
    try {
        int magic = r.readInt();
        if (magic != ForgeVersions.INDEX_MAGIC) {
            throw new ForgeFormatException("bad index magic", 0);
        }
        int count = r.readInt();
        BPlusTree tree = new BPlusTree();
        tree.root = decodeNode(r);
        tree.size = count;
        return tree;
    } catch (java.io.EOFException e) {
        throw new ForgeFormatException("truncated index", r.position(), e);
    }
}

private static Node decodeNode(BinaryReader r) throws java.io.EOFException, ForgeFormatException {
    byte tag = r.readByte();
    if (tag == 0) {
        LeafNode leaf = new LeafNode();
        int n = r.readVarInt();
        for (int i = 0; i < n; i++) {
            leaf.keys.add(r.readBytes(r.readVarInt()));
            leaf.values.add(r.readBytes(r.readVarInt()));
        }
        return leaf;
    }
    InternalNode internal = new InternalNode();
    int kn = r.readVarInt();
    for (int i = 0; i < kn; i++) internal.keys.add(r.readBytes(r.readVarInt()));
    int cn = r.readVarInt();
    for (int i = 0; i < cn; i++) internal.children.add(decodeNode(r));
    return internal;
}

private abstract static class Node {
    abstract boolean isFull();
}

private static final class LeafNode extends Node {
    final List<byte[]> keys = new ArrayList<>();
    final List<byte[]> values = new ArrayList<>();
    LeafNode next;

    boolean isFull() { return keys.size() >= ORDER; }

    Optional<byte[]> get(byte[] key) {
        int idx = Collections.binarySearch(keys, key,
                (a, b) -> {
                    int len = Math.min(a.length, b.length);
                    for (int i = 0; i < len; i++) {
                        int d = Byte.compare(a[i], b[i]);
                        if (d != 0) return d;
                    }
                    return Integer.compare(a.length, b.length);
                });
        return idx >= 0 ? Optional.of(values.get(idx)) : Optional.empty();
    }
}

private static final class InternalNode extends Node {
    final List<byte[]> keys = new ArrayList<>();
    final List<Node> children = new ArrayList<>();

    boolean isFull() { return keys.size() >= ORDER - 1; }
}

}
