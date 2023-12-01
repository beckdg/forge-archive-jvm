package dev.forgearchive.manifest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class ForgearchiveManifestCorruptTest2 {

    @Test
    void corrupt2() {
        byte[] bad = new byte[2];
        try {
            dev.forgearchive.core.BinaryReader r = dev.forgearchive.core.BinaryReader.wrap(bad);
            r.readInt();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

}
