package dev.forgearchive.recovery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;


class ForgearchiveRecoveryConcurrencyTest1 {

    @Test
    void concurrent1() throws Exception {
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            new Thread(() -> { latch.countDown(); }).start();
        }
        latch.await(5, java.util.concurrent.TimeUnit.SECONDS);
        assertEquals(0, latch.getCount());
    }

}
