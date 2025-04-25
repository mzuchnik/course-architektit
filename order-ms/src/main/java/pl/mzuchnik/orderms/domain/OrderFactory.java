package pl.mzuchnik.orderms.domain;

import java.time.LocalDateTime;
import java.util.List;

public class OrderFactory {

    public static Order create(OrderId id, List<OrderItem> orderItems, CustomerId customerId, LocalDateTime createdAt) {
        return new Order(id, orderItems, customerId, createdAt);
    }

    public static Order createNewOrder(List<OrderItem> orderItems, CustomerId customerId) {
        return new Order(OrderId.createNew(), orderItems, customerId, LocalDateTime.now());
    }
}
