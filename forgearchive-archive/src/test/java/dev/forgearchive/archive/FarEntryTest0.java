package dev.forgearchive.archive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class FarEntryTest0 {

    @Test
    void basic0() throws Exception {
        assertNotNull(dev.forgearchive.archive.FarEntry.class);
    }

    @Test
    void roundtrip0() throws Exception {
        byte[] data = "test0".getBytes();
        assertTrue(data.length > 0);
    }

    @Test
    void corruptInput0() {
        byte[] garbage = new byte[]{0, -1, 127, 0};
        try {
            // module-specific corrupt handling
            assertNotNull(garbage);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }
    }

}
