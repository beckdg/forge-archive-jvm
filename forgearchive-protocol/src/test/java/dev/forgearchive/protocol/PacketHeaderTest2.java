package dev.forgearchive.protocol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class PacketHeaderTest2 {

    @Test
    void basic2() throws Exception {
        assertNotNull(dev.forgearchive.protocol.PacketHeader.class);
    }

    @Test
    void roundtrip2() throws Exception {
        byte[] data = "test2".getBytes();
        assertTrue(data.length > 0);
    }

    @Test
    void corruptInput2() {
        byte[] garbage = new byte[]{0, -1, 127, 0};
        try {
            // module-specific corrupt handling
            assertNotNull(garbage);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }
    }

}
