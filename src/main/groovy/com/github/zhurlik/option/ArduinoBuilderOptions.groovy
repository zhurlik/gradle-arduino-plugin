package com.github.zhurlik.option

/**
 * See https://github.com/arduino/arduino-builder
 */
class ArduinoBuilderOptions {
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
}
