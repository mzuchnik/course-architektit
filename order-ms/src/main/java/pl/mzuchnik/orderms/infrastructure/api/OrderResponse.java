package pl.mzuchnik.orderms.infrastructure.api;

import pl.mzuchnik.orderms.domain.Order;

import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID uuid,
        List<OrderItemResponse> items,
        UUID customerUuid,
        double totalPrice
) {
    public record OrderItemResponse(
            UUID productUuid,
            int quantity,
            double unitPrice
    ){}

    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getOrderId().uuid(),
                order.getOrderItems().stream()
                        .map(x -> new OrderItemResponse(
                                x.bookId().uuid(),
                                x.quantity(),
                                x.price().amount().doubleValue()))
                        .toList(),
                order.getCustomerId().uuid(),
                order.getTotalPrice().amount().doubleValue());
    }
}
