package dev.forgearchive.protocol;


public final class PacketDecoder {
    public record DecodedPacket(PacketHeader header, byte[] payload) {}

    public DecodedPacket decode(byte[] frame) throws Exception {
        PacketHeader hdr = PacketHeader.decode(java.util.Arrays.copyOf(frame, 20));
        byte[] payload = java.util.Arrays.copyOfRange(frame, 20, 20 + hdr.length());
        return new DecodedPacket(hdr, payload);
    }

}
