package pl.mzuchnik.orderms.domain;

import java.math.BigDecimal;

public record OrderItem(
        BookId bookId,
        int quantity,
        Price price
) {

    public OrderItem{
        if(bookId == null) {
            throw new IllegalArgumentException("BookId cannot be null");
        }
        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if(price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
    }

    public Price getOrderItemTotalPrice(){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static OrderItem create(BookId bookId, int quantity, Price price) {
        return new OrderItem(bookId, quantity, price);
    }
}
