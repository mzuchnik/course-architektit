package pl.mzuchnik.orderms.infrastructure.adapters;

import pl.mzuchnik.orderms.domain.*;

import java.math.BigDecimal;
import java.util.stream.Collectors;

class OrderEntityMapper {

    public static OrderJpaEntity toJpaEntity(Order order) {

        OrderJpaEntity orderJpaEntity = new OrderJpaEntity();
        orderJpaEntity.setUuid(order.getOrderId().uuid());
        orderJpaEntity.setCustomerUuid(order.getCustomerId().uuid());
        orderJpaEntity.setOrderItems(order.getOrderItems().stream().map(x -> new OrderItemJpaCollection(
                x.bookId().uuid(),
                x.quantity(),
                x.price().amount().doubleValue())
        ).collect(Collectors.toSet()));
        orderJpaEntity.setCreatedAt(order.getOrderDate());

        return orderJpaEntity;
    }

    public static Order toDomainEntity(OrderJpaEntity orderJpaEntity) {
        return OrderFactory.create(
                OrderId.create(orderJpaEntity.getUuid()),
                orderJpaEntity.getOrderItems()
                        .stream()
                        .map(x -> OrderItem.create(
                                new BookId(x.getProductUuid()),
                                x.getQuantity(),
                                new Price(new BigDecimal(String.valueOf(x.getUnitPrice())))))
                        .toList(),
                new CustomerId(orderJpaEntity.getCustomerUuid()),
                orderJpaEntity.getCreatedAt());

    }
}
