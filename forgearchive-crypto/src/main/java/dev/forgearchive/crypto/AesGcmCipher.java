package dev.forgearchive.crypto;


import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public final class AesGcmCipher {
    private static final int TAG_BITS = 128;
    private final byte[] key;

    public AesGcmCipher(byte[] key) {
        if (key.length != 16 && key.length != 24 && key.length != 32)
            throw new IllegalArgumentException("invalid key length");
        this.key = key.clone();
    }

    public byte[] encrypt(byte[] plaintext) throws Exception {
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(TAG_BITS, iv));
        byte[] ct = cipher.doFinal(plaintext);
        byte[] out = new byte[iv.length + ct.length];
        System.arraycopy(iv, 0, out, 0, iv.length);
        System.arraycopy(ct, 0, out, iv.length, ct.length);
        return out;
    }

    public byte[] decrypt(byte[] ciphertext) throws Exception {
        byte[] iv = new byte[12];
        System.arraycopy(ciphertext, 0, iv, 0, 12);
        byte[] ct = new byte[ciphertext.length - 12];
        System.arraycopy(ciphertext, 12, ct, 0, ct.length);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(TAG_BITS, iv));
        return cipher.doFinal(ct);
    }

}
