package com.github.zhurlik.option

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WarningsTest {
    @Test
    void testMain() {
        Assertions.assertEquals('[none, default, more, all]', Warnings.values().toArrayString())
    }
}
