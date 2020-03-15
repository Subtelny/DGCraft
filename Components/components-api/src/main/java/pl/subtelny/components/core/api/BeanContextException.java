package pl.subtelny.components.core.api;

public class BeanContextException extends RuntimeException {

    BeanContextException(String message) {
        super(message);
    }

    BeanContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BeanContextException of(String message) {
        return new BeanContextException(message);
    }

    public static BeanContextException of(String message, Throwable cause) {
        return new BeanContextException(message, cause);
    }

}
