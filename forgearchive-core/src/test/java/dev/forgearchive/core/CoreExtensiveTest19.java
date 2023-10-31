package dev.forgearchive.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class CoreExtensiveTest19 {

    @Test
    void testVarIntRoundtrip19() throws Exception {
        int v = 2414;
        byte[] buf = new byte[10];
        int off = VarInt.writeUnsigned(buf, 0, v);
        BinaryReader r = BinaryReader.wrap(java.util.Arrays.copyOf(buf, off));
        assertEquals(v, r.readVarInt());
    }

    @Test
    void testChecksum19() {
        byte[] data = new byte[20];
        for (int j = 0; j < data.length; j++) data[j] = (byte) j;
        long c = Checksum.crc32c(data);
        assertTrue(c >= 0);
    }

    @Test
    void testContentHash19() {
        byte[] data = ("payload19").getBytes();
        ContentHash h1 = ContentHash.sha256(data);
        ContentHash h2 = ContentHash.sha256(data);
        assertEquals(h1, h2);
        assertEquals(64, h1.hex().length());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 8, 16, 32})
    void testHexRoundtrip19(int len) {
        byte[] data = new byte[len];
        for (int j = 0; j < len; j++) data[j] = (byte) (19 + j);
        assertArrayEquals(data, Hex.decode(Hex.encode(data)));
    }

}
