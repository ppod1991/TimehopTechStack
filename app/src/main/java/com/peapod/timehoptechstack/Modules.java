package com.peapod.timehoptechstack;

/**
 * Provides the list of modules used by the main application
 */
public final class Modules {
    static Object[] list() {
        return new Object[] {
            new NetworkModule("https://api.github.com")
        };
    }

}
