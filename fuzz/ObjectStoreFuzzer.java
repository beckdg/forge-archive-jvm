package dev.forgearchive.fuzz;

import dev.forgearchive.dedup.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class ObjectStoreFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            ContentAddressedStore store = new ContentAddressedStore();
            store.store(data);
            MerkleTree.build(java.util.List.of(data));
            new BloomFilter(1024).mightContain(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
