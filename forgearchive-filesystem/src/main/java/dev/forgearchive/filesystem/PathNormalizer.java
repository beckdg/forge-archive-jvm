package dev.forgearchive.filesystem;

import java.nio.file.*;
public final class PathNormalizer {
    public String normalize(String path) {
        return Paths.get(path).normalize().toString().replace('\\', '/');
    }

    public boolean isSafe(String path) {
        String n = normalize(path);
        return !n.startsWith("..") && !n.contains("/../");
    }

}
