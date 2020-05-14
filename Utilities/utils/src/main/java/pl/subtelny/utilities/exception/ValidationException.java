package pl.subtelny.utilities.exception;

public class ValidationException extends RuntimeException {

    private final Object[] values;

    public ValidationException(String message, Object... values) {
        super(message);
        this.values = values;
    }

    public Object[] getValues() {
        return values;
    }

    public static ValidationException of(String message) {
        return new ValidationException(message);
    }

    public static ValidationException of(String message, Object... values) {
        return new ValidationException(message, values);
    }

}
