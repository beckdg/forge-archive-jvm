package dev.forgearchive.diff;


public final class DeltaEncoder {
    public byte[] encode(byte[] base, byte[] target) {
        dev.forgearchive.core.BinaryWriter w = new dev.forgearchive.core.BinaryWriter();
        int i = 0;
        while (i < target.length) {
            int run = 0;
            while (i + run < target.length && run < 255
                && i + run < base.length && target[i + run] == base[i + run]) run++;
            if (run > 0) {
                w.writeByte((byte) 0);
                w.writeByte((byte) run);
                i += run;
                continue;
            }
            int lit = Math.min(255, target.length - i);
            w.writeByte((byte) 1);
            w.writeByte((byte) lit);
            w.writeBytes(java.util.Arrays.copyOfRange(target, i, i + lit));
            i += lit;
        }
        w.writeByte((byte) -1);
        return w.toByteArray();
    }

}
