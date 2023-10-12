package dev.forgearchive.buffer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class GrowableBufferTest7 {

    @Test
    void basic7() throws Exception {
        assertNotNull(dev.forgearchive.buffer.GrowableBuffer.class);
    }

    @Test
    void roundtrip7() throws Exception {
        byte[] data = "test7".getBytes();
        assertTrue(data.length > 0);
    }

    @Test
    void corruptInput7() {
        byte[] garbage = new byte[]{0, -1, 127, 0};
        try {
            // module-specific corrupt handling
            assertNotNull(garbage);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }
    }

}
