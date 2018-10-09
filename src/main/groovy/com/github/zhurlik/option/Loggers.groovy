package com.github.zhurlik.option

/**
 *  -logger: Optional, can be "human", "humantags" or "machine". Defaults to "human".
 */
enum Loggers {
    /**
     * Defaults to "human".
     */
    HUMAN('human'),

    /**
     * If "humantags" the messages are qualified with a prefix that indicates their level (info, debug, error).
     */
    HUMANTAGS('humantags'),

    /**
     * If "machine", messages emitted will be in a format which the Arduino IDE understands and that it uses for I18N.
     */
    MACHINE('machine')

    private final String name

    Loggers(final String name) {
        this.name = name
    }

    @Override
    String toString() {
        return name;
    }
}
