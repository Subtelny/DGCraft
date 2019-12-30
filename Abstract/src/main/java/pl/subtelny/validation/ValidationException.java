package pl.subtelny.validation;

public class ValidationException extends RuntimeException {

	public ValidationException(String message) {
		super(message);
	}

	public static ValidationException of(String message) {
		return new ValidationException(message);
	}

}
