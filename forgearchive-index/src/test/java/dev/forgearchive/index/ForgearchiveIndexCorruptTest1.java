package dev.forgearchive.index;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class ForgearchiveIndexCorruptTest1 {

    @Test
    void corrupt1() {
        byte[] bad = new byte[1];
        try {
            dev.forgearchive.core.BinaryReader r = dev.forgearchive.core.BinaryReader.wrap(bad);
            r.readInt();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

}
