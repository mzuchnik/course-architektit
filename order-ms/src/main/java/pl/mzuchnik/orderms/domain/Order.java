package pl.mzuchnik.orderms.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private OrderId orderId;
    private List<OrderItem> orderItems;
    private CustomerId customerId;
    private LocalDateTime orderDate;

    Order(OrderId orderId,
          List<OrderItem> orderItems,
          CustomerId customerId,
          LocalDateTime orderDate) {
        validateOrder(orderId, orderItems, customerId, orderDate);
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public void add(OrderItem orderItem) {
        if(orderItem == null) {
            throw new IllegalArgumentException("OrderItem should not be null");
        }
        orderItems.add(orderItem);
    }

    public void remove(OrderItem orderItem) {
        if(orderItem == null) {
            throw new IllegalArgumentException("OrderItem should not be null");
        }
        orderItems.remove(orderItem);
    }

    public Price getTotalPrice() {
        Price totalPrice = Price.ZERO;

        for(OrderItem orderItem : orderItems) {
            totalPrice = totalPrice.add(orderItem.getOrderItemTotalPrice());
        }

        return totalPrice;
    }

    private void validateOrder(OrderId orderId,
                               List<OrderItem> orderItems,
                               CustomerId customerId,
                               LocalDateTime orderDate) {
        if(orderId == null) {
            throw new IllegalArgumentException("Order id cannot be null");
        }
        if(orderItems == null) {
            throw new IllegalArgumentException("Order items cannot be null");
        }
        if(customerId == null) {
            throw new IllegalArgumentException("Customer id cannot be null");
        }
        if(orderDate == null) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
