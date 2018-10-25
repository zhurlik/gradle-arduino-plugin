package com.github.zhurlik.option

import org.apache.commons.io.FileUtils

import java.nio.file.Paths
import java.time.LocalTime
import java.util.stream.Stream

/**
 * See https://github.com/arduino/arduino-builder
 */
class ArduinoBuilderOptions extends AbstractOptions {
    /**
     * -compile or -dump-prefs or -preprocess:
     * Optional. If omitted, defaults to -compile.
     *
     * -dump-prefs will just print all build preferences used,
     * -compile will use those preferences to run the actual compiler,
     * -preprocess will only print preprocessed code to stdout.
     */
    Actions action = Actions.COMPILE

    /**
     * -hardware: Mandatory. Folder containing Arduino platforms.
     *
     * An example is the hardware folder shipped with the Arduino IDE, or the packages folder created by Arduino Boards Manager.
     * Can be specified multiple times. If conflicting hardware definitions are specified, the last one wins.
     */

    List<String> hardware = new ArrayList<>(1)

    /**
     *  -tools: Mandatory. Folder containing Arduino tools (gcc, avrdude...).
     *
     * An example is the hardware/tools folder shipped with the Arduino IDE, or the packages folder created by Arduino Boards Manager.
     * Can be specified multiple times.
     */
    List<String> tools = new ArrayList<>(1)

    /**
     * -libraries: Optional. Folder containing Arduino libraries.
     *
     * An example is the libraries folder shipped with the Arduino IDE.
     * Can be specified multiple times.
     */
    List<String> libraries = new ArrayList<>()

    /**
     * -fqbn: Mandatory. Fully Qualified Board Name, e.g.: arduino:avr:uno
     */
    String fqbn

    /**
     *  -build-path: Optional. Folder where to save compiled files.
     *  If omitted, a folder will be created in the temporary folder specified by your OS.
     */
    String buildPath

    /**
     *  -prefs=key=value: Optional.
     *  It allows to override some build properties.
     */
    Map<String, String> prefs = [:]

    /**
     * -warnings: Optional, can be "none", "default", "more" and "all".
     * Defaults to "none". Used to tell gcc which warning level to use (-W flag).
     */
    Warnings warning = Warnings.DEFAULT

    /**
     *  -verbose: Optional, turns on verbose mode.
     */
    boolean verbose = false

    /**
     * -quiet: Optional, supresses almost every output.
     */
    boolean quite = false

    /**
     * -debug-level: Optional, defaults to "5". Used for debugging.
     * Set it to 10 when submitting an issue.
     */
    byte debugLevel = 5

    /**
     * -core-api-version: Optional, defaults to "10600".
     * The version of the Arduino IDE which is using this tool.
     */
    String coreApiVersion = '10600'

    /**
     * -logger: Optional, can be "human", "humantags" or "machine". Defaults to "human".
     */
    Loggers logger = Loggers.HUMAN

    /**
     * -version: if specified, prints version and exits.
     */
    boolean version = false

    /**
     *  -build-options-file: it specifies path to a local build.options.json file (see paragraph below),
     *  which allows you to omit specifying params such as -hardware, -tools, -libraries, -fqbn, -pref and -ide-version.
     */
    String buildOptionsFile

    /**
     * -vid-pid: when specified, VID/PID specific build properties are used, if boards supports them.
     */
    String vidPid

    /**
     * -ide-version=10807
     */
    String ideVersion

    /**
     * -built-in-libraries /opt/arduino-1.8.7/libraries
     */
    String builtInLibraries

    /**
     * -build-cache /tmp/arduino_cache_294101
     */
    String buildCache

    void addBuiltInLibraries(final String ideHome) {
        /**
         * -built-in-libraries /opt/arduino-1.8.7/libraries
         */
        this.builtInLibraries = Paths.get(ideHome, 'libraries').toString()
    }

    void addHardware(final String ideHome) {
        /**
         * -hardware /opt/arduino-1.8.7/hardware
         */
        this.hardware.add(Paths.get(ideHome, 'hardware').toString())
    }

    void addBuildCache() {
        if (this.buildCache == null) {
            this.buildCache = Paths.get(FileUtils.getTempDirectory().toString(),
                    "arduino_cache_${LocalTime.now().getNano().toString()}")
        }
    }

    void addBuildPath() {
        if (this.buildPath == null) {
            this.buildPath = Paths.get(FileUtils.getTempDirectory().toString(),
                    "arduino_build_${LocalTime.now().getNano().toString()}")
        }
    }

    void addTools(final String ideHome) {
        /**
         * -tools /opt/arduino-1.8.7/tools-builder
         * -tools /opt/arduino-1.8.7/hardware/tools/avr
         */
        [
                Paths.get(ideHome, 'tools-builder'),
                Paths.get(ideHome, 'hardware', 'tools', 'avr')
        ].each { this.tools.add(it.toString()) }
    }

    void addLibraries() {
        if (this.libraries.isEmpty()) {
            // -libraries /home/zhurlik/Arduino/libraries
            this.libraries.add(Paths.get(System.properties['user.home'] as String, 'Arduino', 'libraries').toString())
        }
    }

    void addPrefs(final String ideHome) {
        /**
         * -prefs=build.warn_data_percentage=75
         * -prefs=runtime.tools.avrdude.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.avrdude-6.3.0-arduino14.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.arduinoOTA.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.arduinoOTA-1.2.1.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.avr-gcc.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.avr-gcc-5.4.0-atmel3.6.1-arduino2.path=/opt/arduino-1.8.7/hardware/tools/avr
         */
        if (prefs.isEmpty()) {
            this.prefs['build.warn_data_percentage'] = '75'
            final String avrDir = Paths.get(ideHome, 'hardware', 'tools', 'avr').toString()
            this.prefs['runtime.tools.avrdude.path'] = avrDir
            this.prefs['runtime.tools.avrdude-6.3.0-arduino14.path'] = avrDir
            this.prefs['runtime.tools.arduinoOTA.path'] = avrDir
            this.prefs['runtime.tools.arduinoOTA-1.2.1.path'] = avrDir
            this.prefs['runtime.tools.avr-gcc.path'] = avrDir
            this.prefs['runtime.tools.avr-gcc-5.4.0-atmel3.6.1-arduino2.path'] = avrDir
        }
    }

    void validate(final Actions expected) {
        if (expected != action) {
            throw new IllegalArgumentException("Action should be: $expected")
        }

        // -hardware: Mandatory. Folder containing Arduino platforms.
        if (hardware.isEmpty()) {
            throw new IllegalArgumentException('-hardware option is mandatory')
        }
        // -tools: Mandatory. Folder containing Arduino tools (gcc, avrdude...)
        if (tools.isEmpty()) {
            throw new IllegalArgumentException('-tools option is mandatory')
        }
        // -fqbn: Mandatory. Fully Qualified Board Name, e.g.: arduino:avr:uno
        if (fqbn in [null, '']) {
            throw new IllegalArgumentException('-fqbn option is mandatory')
        }
    }

    /**
     * To be able to modify options.
     *
     * @param closure
     */
    void customize(final Closure closure) {
        closure()
    }

    /**
     * To be able to use in Exec tasks.
     *
     * @return list of strings
     */
    List<String> convertToArgs() {
        final List<String> args = new LinkedList<>()
        Stream.of(
                addOption(action.toString()),
                addOption('logger', logger.toString()),
                addOptions('hardware', hardware),
                addOptions('tools', tools),
                addOnlyNotNullOption('built-in-libraries', builtInLibraries),
                addOptions('libraries', libraries),
                addOption('fqbn', fqbn),
                addOption('ide-version', ideVersion),
                addOnlyNotNullOption('build-path', buildPath),
                addOption('warnings', warning.toString()),
                addOnlyNotNullOption('build-cache', buildCache),
                addMap('prefs', prefs),
                addFlag('verbose', verbose)
        ).forEach { it.accept(args) }

        return args
    }
}
