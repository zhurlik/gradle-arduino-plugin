package com.github.zhurlik.option

import java.util.function.Consumer

/**
 * Common methods for options that can be used in the Arduino Studio tools.
 *
 * @author zhurlik@gmail.com
 */
abstract class AbstractOptions {
    /**
     * Convert a map to a few options like:
     *          -name=key1=value1
     *          -name=key2=value2
     *          ....
     *
     * @param name
     * @param map
     * @return a function for updating a list
     */
    static Consumer<List<String>> addMap(final String name, final Map<String, String> map) {
        { opts ->
            map.collect {
                "-$name=${it.key}=${it.value}"
            }.forEach { opts.add(it) }
        }
    }

    /**
     * Adds a few options like:
     *          -name
     *          value1
     *          -name
     *          value2
     *          ....
     *
     * @param name
     * @param values
     * @return a function for updating a list
     */
    static Consumer<List<String>> addOptions(final String name, final List<String> values) {
        { opts ->
            values.forEach {
                opts.add("-$name")
                opts.add(it)
            }
        }
    }

    /**
     * Adds something like: -value
     *
     * @param value
     * @return a function for updating a list
     */
    static Consumer<List<String>> addOption(final String value) {
        { opts -> opts.add("-$value") }
    }

    /**
     * Adds something like: -name=value
     *
     * @param name
     * @param value
     * @return a function for updating a list
     */
    static Consumer<List<String>> addOption(final String name, final String value) {
        { opts -> opts.add("-$name=$value") }
    }

    /**
     * Adds when value is not null:
     *              -name
     *              value
     *
     * @param name
     * @param value
     * @return a function for updating a list
     */
    static Consumer<List<String>> addOnlyNotNullOption(final String name, final String value) {
        { opts ->
            if (!(value in [null, ''])) {
                opts.add("-$name")
                opts.add(value)
            }
        }
    }

    /**
     * Adds a flag: -name
     *
     * @param name
     * @param flag
     * @return a function for updating a list
     */
    static Consumer<List<String>> addFlag(final String name, final boolean flag) {
        { opts ->
            if (flag) {
                opts.add("-$name")
            }
        }
    }
}
