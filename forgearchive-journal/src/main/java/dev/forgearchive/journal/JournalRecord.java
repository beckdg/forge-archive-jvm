package dev.forgearchive.journal;

import dev.forgearchive.core.*; import java.util.Arrays;
public final class JournalRecord {
    public enum Op { APPEND(1), DELETE(2), TRUNCATE(3);
        final int id; Op(int id) { this.id = id; }
    }
    private final Op op;
    private final String target;
    private final byte[] payload;
    private final long seq;

    public JournalRecord(Op op, String target, byte[] payload, long seq) {
        this.op = op; this.target = target;
        this.payload = payload == null ? new byte[0] : payload.clone();
        this.seq = seq;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        w.writeInt(op.id);
        w.writeLong(seq);
        byte[] tb = target.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        w.writeVarInt(tb.length);
        w.writeBytes(tb);
        w.writeVarInt(payload.length);
        w.writeBytes(payload);
        return w.toByteArray();
    }

    public static JournalRecord decode(BinaryReader r) throws Exception {
        int opId = r.readInt();
        long seq = r.readLong();
        int tlen = r.readVarInt();
        String target = r.readUtf8(tlen);
        int plen = r.readVarInt();
        byte[] payload = r.readBytes(plen);
        Op op = Arrays.stream(Op.values()).filter(o -> o.id == opId).findFirst()
            .orElseThrow(() -> new ForgeFormatException("OP", "bad op"));
        return new JournalRecord(op, target, payload, seq);
    }

    public Op op() { return op; }
    public String target() { return target; }
    public byte[] payload() { return payload.clone(); }
    public long seq() { return seq; }

}
