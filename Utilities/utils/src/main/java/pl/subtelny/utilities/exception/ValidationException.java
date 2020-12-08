package pl.subtelny.utilities.exception;

public class ValidationException extends RuntimeException {

    private final Object[] values;

    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
        this.values = new Object[0];
    }

    public ValidationException(String message, Throwable throwable, Object... values) {
        super(message, throwable);
        this.values = values;
    }

    public ValidationException(String message, Object... values) {
        super(message);
        this.values = values;
    }

    public Object[] getValues() {
        return values;
    }

    public static ValidationException of(String message, ValidationException e) {
        return new ValidationException(message, e, e.getValues());
    }

    public static ValidationException of(String message) {
        return new ValidationException(message);
    }

    public static ValidationException of(String message, Object... values) {
        return new ValidationException(message, values);
    }

}
