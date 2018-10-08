package com.github.zhurlik.option

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class ActionsTest {

    @Test
    void testMain() {
        assertEquals('[compile, dump-prefs, preprocess]', Actions.values().toArrayString())
    }
}
