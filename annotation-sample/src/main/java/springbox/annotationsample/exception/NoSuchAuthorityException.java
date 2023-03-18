package springbox.annotationsample.exception;

public class NoSuchAuthorityException extends RuntimeException {
    public NoSuchAuthorityException(String message) {
        super(message);
    }
}
