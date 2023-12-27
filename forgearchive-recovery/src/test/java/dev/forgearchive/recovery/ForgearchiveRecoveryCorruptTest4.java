package dev.forgearchive.recovery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class ForgearchiveRecoveryCorruptTest4 {

    @Test
    void corrupt4() {
        byte[] bad = new byte[4];
        try {
            dev.forgearchive.core.BinaryReader r = dev.forgearchive.core.BinaryReader.wrap(bad);
            r.readInt();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

}
