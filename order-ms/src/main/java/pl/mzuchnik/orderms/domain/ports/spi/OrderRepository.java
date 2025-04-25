package pl.mzuchnik.orderms.domain.ports.spi;

import pl.mzuchnik.orderms.domain.Order;
import pl.mzuchnik.orderms.domain.OrderId;
import pl.mzuchnik.orderms.domain.OrderNotFoundException;

import java.util.List;

public interface OrderRepository {

    Order createOrder(Order order);

    List<Order> getAllOrders();

    Order getOrderById(OrderId id) throws OrderNotFoundException;
}
