package dev.forgearchive.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

public final class ContentHash {
    private final byte[] digest;

    private ContentHash(byte[] digest) {
        this.digest = Arrays.copyOf(digest, digest.length);
    }

    public static ContentHash sha256(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return new ContentHash(md.digest(data));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static ContentHash ofDigest(byte[] digest) {
        if (digest.length != 32) throw new IllegalArgumentException("expected 32 bytes");
        return new ContentHash(digest);
    }

    public byte[] bytes() { return Arrays.copyOf(digest, digest.length); }

    public String hex() { return Hex.encode(digest); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentHash other)) return false;
        return Arrays.equals(digest, other.digest);
    }

    @Override
    public int hashCode() { return Arrays.hashCode(digest); }

    @Override
    public String toString() { return hex(); }
}
