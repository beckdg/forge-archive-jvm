package dev.forgearchive.rpc;


public final class RpcDecoder {
    public RpcMessage decodeFrame(byte[] frame) throws Exception {
        return RpcMessage.decode(frame);
    }

}
