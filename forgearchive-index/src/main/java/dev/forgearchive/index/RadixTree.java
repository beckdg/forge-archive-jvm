package dev.forgearchive.index;

import java.util.*;
public final class RadixTree {
    private final Node root = new Node();

    static class Node {
        Map<Character, Node> children = new HashMap<>();
        byte[] value;
    }

    public void put(String key, byte[] value) {
        Node n = root;
        for (char c : key.toCharArray()) n = n.children.computeIfAbsent(c, k -> new Node());
        n.value = value;
    }

    public Optional<byte[]> get(String key) {
        Node n = root;
        for (char c : key.toCharArray()) {
            n = n.children.get(c);
            if (n == null) return Optional.empty();
        }
        return Optional.ofNullable(n.value);
    }

}
