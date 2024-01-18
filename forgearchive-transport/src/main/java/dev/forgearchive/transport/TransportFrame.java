package dev.forgearchive.transport;

import dev.forgearchive.protocol.*;
public final class TransportFrame {
    public byte[] wrap(int type, byte[] payload, long seq) {
        PacketHeader hdr = new PacketHeader(type, payload.length, seq);
        byte[] h = hdr.encode();
        byte[] out = new byte[h.length + payload.length];
        System.arraycopy(h, 0, out, 0, h.length);
        System.arraycopy(payload, 0, out, h.length, payload.length);
        return out;
    }

}
