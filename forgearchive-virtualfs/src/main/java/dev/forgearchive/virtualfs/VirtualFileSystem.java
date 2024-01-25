package dev.forgearchive.virtualfs;

import java.util.*;
public final class VirtualFileSystem {
    private final Map<String, VirtualInode> nodes = new HashMap<>();

    public void mkdir(String path) {
        nodes.put(path, new VirtualInode(path, VirtualInode.Type.DIR));
    }

    public void writeFile(String path, byte[] data) {
        VirtualInode n = new VirtualInode(path, VirtualInode.Type.FILE);
        n.setContent(data);
        nodes.put(path, n);
    }

    public Optional<byte[]> readFile(String path) {
        VirtualInode n = nodes.get(path);
        if (n == null || n.type() != VirtualInode.Type.FILE) return Optional.empty();
        return Optional.of(n.content());
    }

    public Set<String> list(String dir) {
        Set<String> out = new TreeSet<>();
        String prefix = dir.endsWith("/") ? dir : dir + "/";
        for (String p : nodes.keySet()) {
            if (p.startsWith(prefix)) out.add(p);
        }
        return out;
    }

}
