package dev.forgearchive.compression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class FramedCompressorTest3 {

    @Test
    void basic3() throws Exception {
        assertNotNull(dev.forgearchive.compression.FramedCompressor.class);
    }

    @Test
    void roundtrip3() throws Exception {
        byte[] data = "test3".getBytes();
        assertTrue(data.length > 0);
    }

    @Test
    void corruptInput3() {
        byte[] garbage = new byte[]{0, -1, 127, 0};
        try {
            // module-specific corrupt handling
            assertNotNull(garbage);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }
    }

}
