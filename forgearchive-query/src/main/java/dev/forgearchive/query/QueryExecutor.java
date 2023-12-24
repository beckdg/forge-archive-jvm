package dev.forgearchive.query;


import dev.forgearchive.archive.*;
import dev.forgearchive.index.*;
import java.util.*;

public final class QueryExecutor {
    public List<String> execute(ArchiveReader reader, QueryParser.Query q) throws Exception {
        BPlusTree index = new BPlusTree(16);
        for (FarEntry e : reader.entries()) index.put(e.path(), e.hash().bytes());
        List<String> hits = new ArrayList<>();
        for (FarEntry e : reader.entries()) {
            if (matches(e, q)) hits.add(e.path());
        }
        return hits;
    }

    private boolean matches(FarEntry e, QueryParser.Query q) {
        return switch (q.field()) {
            case "path" -> e.path().contains(q.value());
            case "size" -> compareLong(e.uncompressedSize(), q.op(), Long.parseLong(q.value()));
            default -> false;
        };
    }

    private boolean compareLong(long v, String op, long target) {
        return switch (op) {
            case ">" -> v > target;
            case "<" -> v < target;
            case "=" -> v == target;
            default -> false;
        };
    }

}
