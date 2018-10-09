package com.github.zhurlik.option

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class LoggersTest {

    @Test
    void testMain() {
        assertEquals('[human, humantags, machine]', Loggers.values().toArrayString())
    }
}
