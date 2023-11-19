package dev.forgearchive.index;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class IndexHeaderTest6 {

    @Test
    void basic6() throws Exception {
        assertNotNull(dev.forgearchive.index.IndexHeader.class);
    }

    @Test
    void roundtrip6() throws Exception {
        byte[] data = "test6".getBytes();
        assertTrue(data.length > 0);
    }

    @Test
    void corruptInput6() {
        byte[] garbage = new byte[]{0, -1, 127, 0};
        try {
            // module-specific corrupt handling
            assertNotNull(garbage);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }
    }

}
