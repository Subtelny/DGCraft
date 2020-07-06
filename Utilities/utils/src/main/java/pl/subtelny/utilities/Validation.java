package pl.subtelny.utilities;

import pl.subtelny.utilities.exception.ValidationException;

public final class Validation {

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw ValidationException.of(message);
        }
    }

}
