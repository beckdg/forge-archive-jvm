package dev.forgearchive.compression;

import java.io.*; import java.util.zip.*;
public final class DeflateCodec {
    public byte[] compress(byte[] input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (DeflaterOutputStream def = new DeflaterOutputStream(bos)) { def.write(input); }
        return bos.toByteArray();
    }
    public byte[] decompress(byte[] input) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (InflaterInputStream inf = new InflaterInputStream(bis)) { inf.transferTo(bos); }
        return bos.toByteArray();
    }

}
