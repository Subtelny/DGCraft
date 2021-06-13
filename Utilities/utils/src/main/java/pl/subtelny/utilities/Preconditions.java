package pl.subtelny.utilities;

public final class Preconditions {

    private Preconditions() { }

    public static <T> T notNull(T reference, String message) {
        if (reference == null) {
            throw new IllegalStateException(message);
        }
        return reference;
    }

}
