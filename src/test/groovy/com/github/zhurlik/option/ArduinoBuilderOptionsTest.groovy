package com.github.zhurlik.option

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.assertTrue

class ArduinoBuilderOptionsTest {
    private final ArduinoBuilderOptions options = new ArduinoBuilderOptions()

    @Test
    void testDefault() {
        assertSame(Loggers.HUMAN, options.logger)
        assertSame(Warnings.DEFAULT, options.warning)
        assertSame(Actions.COMPILE, options.action)
        assertFalse(options.verbose)
        assertFalse(options.version)
        assertEquals('10600', options.coreApiVersion)
        assertNull(options.vidPid)
        assertEquals(5, options.debugLevel)
        assertFalse(options.quite)
        assertNull(options.fqbn)
        assertNull(options.buildOptionsFile)
        assertNull(options.buildPath)
        assertTrue(options.hardware.isEmpty())
        assertTrue(options.libraries.isEmpty())
        assertTrue(options.prefs.isEmpty())
        assertTrue(options.tools.isEmpty())
    }
}
