package pl.mzuchnik.orderms.domain;

public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
