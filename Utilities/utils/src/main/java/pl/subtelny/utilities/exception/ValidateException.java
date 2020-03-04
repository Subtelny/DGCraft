package pl.subtelny.utilities.exception;

public class ValidateException extends RuntimeException {

    ValidateException(String message) {
        super(message);
    }

    public static ValidateException of(String message) {
        return new ValidateException(message);
    }

}
