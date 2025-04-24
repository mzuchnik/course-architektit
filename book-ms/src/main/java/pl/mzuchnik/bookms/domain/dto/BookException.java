package pl.mzuchnik.bookms.domain.dto;

public class BookException extends RuntimeException {

    public BookException() {
    }

    public BookException(String message) {
        super(message);
    }

    public BookException(String message, Throwable cause) {
        super(message, cause);
    }
}
