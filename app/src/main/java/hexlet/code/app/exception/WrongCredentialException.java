package hexlet.code.app.exception;

public class WrongCredentialException extends RuntimeException {
    public WrongCredentialException(String message) {
        super(message);
    }
}
