package dev.forgearchive.patch;

import dev.forgearchive.diff.DeltaEncoder;
public final class PatchGenerator {
    public byte[] generate(byte[] base, byte[] target) {
        return new DeltaEncoder().encode(base, target);
    }

}
