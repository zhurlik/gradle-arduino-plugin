package com.github.zhurlik.option

import org.apache.commons.io.IOUtils
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

    @Test
    void testConvertToArgs() {
        final String ideHome = '/opt/arduino-1.8.7/'
        final String expected = IOUtils.toString(this.getClass().getResource('/test-options.txt').toURI())
        options.action = Actions.DUMP_PREFS
        options.logger = Loggers.MACHINE
        options.addHardware(ideHome)
        options.addTools(ideHome)
        options.addLibraries()
        options.addPrefs(ideHome)
        options.addBuiltInLibraries(ideHome)
        options.fqbn = 'arduino:avr:nano:cpu=atmega328old'
        options.ideVersion = '10807'
        options.buildPath = '/tmp/arduino_build_921969'
        options.warning=Warnings.ALL
        options.buildCache = '/tmp/arduino_cache_294101'
        options.addPrefs(ideHome)
        options.verbose = true

        assertEquals(expected, options.convertToArgs().join('\n'), "Actual: ${options.convertToArgs().join('\n')}")
    }
}
