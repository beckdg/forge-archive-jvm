package dev.forgearchive.statistics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class ForgearchiveStatisticsCorruptTest5 {

    @Test
    void corrupt5() {
        byte[] bad = new byte[5];
        try {
            dev.forgearchive.core.BinaryReader r = dev.forgearchive.core.BinaryReader.wrap(bad);
            r.readInt();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

}
