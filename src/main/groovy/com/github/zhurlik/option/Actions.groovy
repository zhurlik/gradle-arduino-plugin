package com.github.zhurlik.option

/**
 * One of options for Arduino Builder.
 *
 * See https://github.com/arduino/arduino-builder
 *
 * -compile or -dump-prefs or -preprocess:
 * Optional. If omitted, defaults to -compile.
 */
enum Actions {
    /**
     * -compile will use those preferences to run the actual compiler
     */
    COMPILE('compile'),

    /**
     * -dump-prefs will just print all build preferences used
     */
    DUMP_PREFS('dump-prefs'),

    /**
     * -preprocess will only print preprocessed code to stdout
     */
    PREPROCESS('preprocess')

    private final String command

    Actions(final String command) {
        this.command = command
    }

    @Override
    String toString() {
        return command
    }
}
