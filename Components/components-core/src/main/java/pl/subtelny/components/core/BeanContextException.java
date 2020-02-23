package pl.subtelny.components.core;

class BeanContextException extends RuntimeException {

    BeanContextException(String message) {
        super(message);
    }

    static BeanContextException of(String message) {
        return new BeanContextException(message);
    }

}
