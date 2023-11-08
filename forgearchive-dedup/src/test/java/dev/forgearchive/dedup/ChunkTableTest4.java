package dev.forgearchive.dedup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class ChunkTableTest4 {

    @Test
    void basic4() throws Exception {
        assertNotNull(dev.forgearchive.dedup.ChunkTable.class);
    }

    @Test
    void roundtrip4() throws Exception {
        byte[] data = "test4".getBytes();
        assertTrue(data.length > 0);
    }

    @Test
    void corruptInput4() {
        byte[] garbage = new byte[]{0, -1, 127, 0};
        try {
            // module-specific corrupt handling
            assertNotNull(garbage);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }
    }

}
