package pl.subtelny.components.core.api;

public class ComponentException extends RuntimeException {

    public ComponentException(String message) {
        super(message);
    }

    public ComponentException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ComponentException of(String message) {
        return new ComponentException(message);
    }

    public static ComponentException of(String message, Throwable cause) {
        return new ComponentException(message, cause);
    }

}
