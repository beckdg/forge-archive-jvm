package dev.forgearchive.rpc;

import dev.forgearchive.core.*;
public final class RpcMessage {
    private final int methodId;
    private final byte[] body;
    private final long correlationId;

    public RpcMessage(int methodId, byte[] body, long correlationId) {
        this.methodId = methodId;
        this.body = body.clone();
        this.correlationId = correlationId;
    }

    public byte[] encode() {
        BinaryWriter w = new BinaryWriter();
        w.writeInt(methodId);
        w.writeLong(correlationId);
        w.writeVarInt(body.length);
        w.writeBytes(body);
        return w.toByteArray();
    }

    public static RpcMessage decode(byte[] data) throws Exception {
        BinaryReader r = BinaryReader.wrap(data);
        int mid = r.readInt();
        long cid = r.readLong();
        int blen = r.readVarInt();
        byte[] body = r.readBytes(blen);
        return new RpcMessage(mid, body, cid);
    }

}
