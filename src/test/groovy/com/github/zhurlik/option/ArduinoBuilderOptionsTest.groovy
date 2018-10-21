package com.github.zhurlik.option

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.assertTrue

class ArduinoBuilderOptionsTest {
    private ArduinoBuilderOptions options

    @BeforeEach
    void setUp() {
        options = new ArduinoBuilderOptions()
    }

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
    void testConvertToArgsDefault() {
        final String expected = IOUtils.toString(this.getClass().getResource('/test-default-options.txt').toURI())
        assertEquals(expected, options.convertToArgs().join('\n'), 'Android Builder Options:')
    }

    @Test
    void testDumpPrefsOptions() {
        final String ideHome = '/opt/arduino-1.8.7/'
        final String expected = IOUtils.toString(this.getClass().getResource('/test-dump-prefs-options.txt').toURI())
        options.action = Actions.DUMP_PREFS
        setOptions(ideHome)

        assertEquals(expected, options.convertToArgs().join('\n'), 'Android Builder Dump-Prefs Options:')
    }

    @Test
    void testCompileOptions() {
        final String ideHome = '/opt/arduino-1.8.7/'
        final String expected = IOUtils.toString(this.getClass().getResource('/test-compile-options.txt').toURI())
        options.action = Actions.COMPILE
        setOptions(ideHome)
        assertEquals(expected, options.convertToArgs().join('\n'), 'Android Builder Compile Options:')
    }

    private void setOptions(String ideHome) {
        options.logger = Loggers.MACHINE
        options.addHardware(ideHome)
        options.addTools(ideHome)
        options.libraries = ['/home/zhurlik/Arduino/libraries']
        options.addLibraries()
        options.addPrefs(ideHome)
        options.addBuiltInLibraries(ideHome)
        options.fqbn = 'arduino:avr:nano:cpu=atmega328old'
        options.ideVersion = '10807'
        options.buildPath = '/tmp/arduino_build_921969'
        options.warning = Warnings.ALL
        options.buildCache = '/tmp/arduino_cache_294101'
        options.addPrefs(ideHome)
        options.verbose = true
    }

    @Test
    void testAddBuiltInLibraries() {
        assertNull(options.builtInLibraries)
        options.addBuiltInLibraries('test/ide')
        assertEquals('test/ide/libraries', options.builtInLibraries)
    }

    @Test
    void testAddHardware() {
        assertTrue(options.hardware.isEmpty())
        options.addHardware('test/ide')
        assertEquals('[test/ide/hardware]', options.hardware.toString())
    }

    @Test
    void testAddBuildCache() {
        // default
        assertNull(options.buildCache)
        options.addBuildCache()
        assertNotNull(options.buildCache)
        assertTrue(options.buildCache.contains('arduino_cache'))

        // custom
        options.buildCache = 'my/test/cache'
        options.addBuildCache()
        assertEquals('my/test/cache', options.buildCache)
    }

    @Test
    void testAddBuildPath() {
        // default
        assertNull(options.buildPath)
        options.addBuildPath()
        assertNotNull(options.buildPath)
        assertTrue(options.buildPath.contains('arduino_build'))

        // custom
        options.buildPath = 'my/test/build'
        options.addBuildPath()
        assertEquals('my/test/build', options.buildPath)
    }

    @Test
    void testAddTools() {
        assertTrue(options.tools.isEmpty())
        options.addTools('test/ide')
        assertEquals('[test/ide/tools-builder, test/ide/hardware/tools/avr]', options.tools.toString())
    }

    @Test
    void testAddLibraries() {
        // default
        assertTrue(options.libraries.isEmpty())
        options.addLibraries()
        assertTrue(options.libraries.toString().contains('/Arduino/libraries'))

        // custom
        options.libraries = ['my/libraries/']
        options.addLibraries()
        assertEquals('[my/libraries/]', options.libraries.toString())
    }

    @Test
    void testAddPrefs() {
        // default
        assertTrue(options.prefs.isEmpty())
        options.addPrefs('test/ide')
        assertEquals('[build.warn_data_percentage:75, ' +
                'runtime.tools.avrdude.path:test/ide/hardware/tools/avr, ' +
                'runtime.tools.avrdude-6.3.0-arduino14.path:test/ide/hardware/tools/avr, ' +
                'runtime.tools.arduinoOTA.path:test/ide/hardware/tools/avr, ' +
                'runtime.tools.arduinoOTA-1.2.1.path:test/ide/hardware/tools/avr, ' +
                'runtime.tools.avr-gcc.path:test/ide/hardware/tools/avr, ' +
                'runtime.tools.avr-gcc-5.4.0-atmel3.6.1-arduino2.path:test/ide/hardware/tools/avr]', options.prefs.toString())

        // custom
        options.prefs = ['a': 'b']
        options.addPrefs('test/ide')
        assertEquals('[a:b]', options.prefs.toString())
    }

    @Test
    void testCustomize() {
        assertNull(options.vidPid)
        options.customize {
            options.vidPid = 'test'
        }

        assertEquals('test', options.vidPid)
    }

    @Test
    void testValidateDefaults() {
        try {
            options.validate(Actions.COMPILE)
        } catch (IllegalArgumentException ex) {
            assertEquals('-hardware option is mandatory', ex.getMessage())
        }

        try {
            options.validate(Actions.DUMP_PREFS)
        } catch (IllegalArgumentException ex) {
            assertEquals('Action should be: dump-prefs', ex.getMessage())
        }

        try {
            options.validate(Actions.PREPROCESS)
        } catch (IllegalArgumentException ex) {
            assertEquals('Action should be: preprocess', ex.getMessage())
        }
    }

    @Test
    void testValidateDumpPrefs() {
        options.action = Actions.DUMP_PREFS

        try {
            options.validate(Actions.DUMP_PREFS)
        } catch (IllegalArgumentException ex) {
            assertEquals('-hardware option is mandatory', ex.getMessage())
        }
    }

    @Test
    void testValidatePreprocess() {
        options.action = Actions.PREPROCESS

        try {
            options.validate(Actions.PREPROCESS)
        } catch (IllegalArgumentException ex) {
            assertEquals('-hardware option is mandatory', ex.getMessage())
        }
    }
}
