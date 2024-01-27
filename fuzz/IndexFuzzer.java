package dev.forgearchive.fuzz;

import dev.forgearchive.index.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class IndexFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            if (data.length >= 8) {
              BPlusTree tree = BPlusTree.decode(data);
              tree.get(data);
              new IndexReader().load(data);
            }
            new IndexValidator().validate(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
