package dev.forgearchive.query;


public final class QueryParser {
    public record Query(String field, String op, String value) {}

    public Query parse(String line) {
        String[] parts = line.trim().split("\\s+", 3);
        if (parts.length < 3) throw new IllegalArgumentException("bad query: " + line);
        return new Query(parts[0], parts[1], parts[2]);
    }

}
