package dev.forgearchive.network;

import dev.forgearchive.transport.*;
public final class NetworkSession {
    private long sequence;
    private final TransportFrame framer = new TransportFrame();

    public byte[] send(int type, byte[] payload) {
        return framer.wrap(type, payload, sequence++);
    }

}
