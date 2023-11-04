package dev.forgearchive.crypto;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import dev.forgearchive.core.Hex;

public final class HmacSha256 {
    private final Mac mac;

    public HmacSha256(byte[] key) throws Exception {
        mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
    }

    public byte[] sign(byte[] data) { return mac.doFinal(data); }
    public String signHex(byte[] data) { return Hex.encode(sign(data)); }

    public boolean verify(byte[] data, byte[] signature) {
        byte[] expected = sign(data);
        if (expected.length != signature.length) return false;
        int diff = 0;
        for (int i = 0; i < expected.length; i++) diff |= expected[i] ^ signature[i];
        return diff == 0;
    }

}
