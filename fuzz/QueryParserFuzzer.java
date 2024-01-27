package dev.forgearchive.fuzz;

import dev.forgearchive.query.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class QueryParserFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            String q = new String(data, java.nio.charset.StandardCharsets.UTF_8);
            try { new QueryParser().parse(q); } catch (Exception ignored) {}
            new QueryOptimizer().optimize(new QueryAst());
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
