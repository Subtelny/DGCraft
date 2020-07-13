package pl.subtelny.utilities;

import pl.subtelny.utilities.exception.ValidationException;

public final class Validation {

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw ValidationException.of(message);
        }
    }

    public static void isTrue(boolean condition, String message, Object... objects) {
        if (!condition) {
            throw ValidationException.of(message, objects);
        }
    }

}
