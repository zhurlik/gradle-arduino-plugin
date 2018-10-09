package com.github.zhurlik.option

/**
 * -warnings: Optional, can be "none", "default", "more" and "all".
 *
 * Defaults to "none". Used to tell gcc which warning level to use (-W flag).
 */
enum Warnings {
    NONE('none'),
    DEFAULT('default'),
    MORE('more'),
    ALL('all');

    private final String name

    Warnings(final String name) {
        this.name = name
    }

    @Override
    String toString() {
        return name;
    }
}
