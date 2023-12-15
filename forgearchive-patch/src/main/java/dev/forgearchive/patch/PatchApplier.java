package dev.forgearchive.patch;


public final class PatchApplier {
    public byte[] apply(byte[] base, byte[] delta) throws Exception {
        dev.forgearchive.core.BinaryReader r = dev.forgearchive.core.BinaryReader.wrap(delta);
        byte[] out = base.clone();
        dev.forgearchive.buffer.GrowableBuffer acc = new dev.forgearchive.buffer.GrowableBuffer();
        acc.write(out);
        while (r.remaining() > 0) {
            byte op = r.readByte();
            if (op == -1) break;
            if (op == 0) {
                int run = r.readUnsignedByte();
                for (int i = 0; i < run; i++) acc.writeByte(out[i]);
            } else {
                int lit = r.readUnsignedByte();
                acc.write(r.readBytes(lit));
            }
        }
        return acc.toByteArray();
    }

}
