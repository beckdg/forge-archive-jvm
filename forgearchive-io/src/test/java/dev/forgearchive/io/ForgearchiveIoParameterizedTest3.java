package dev.forgearchive.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class ForgearchiveIoParameterizedTest3 {

    @ParameterizedTest
    @ValueSource(strings = {"a", "ab", "abc3"})
    void param3(String s) {
        assertNotNull(s);
        assertTrue(s.length() >= 1);
    }

}
