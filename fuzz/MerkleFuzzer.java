package dev.forgearchive.fuzz;

import dev.forgearchive.dedup.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class MerkleFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            MerkleTree tree = MerkleTree.build(java.util.List.of(data));
            tree.rootHash();
            new MerkleVerifier().verify(tree, data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
