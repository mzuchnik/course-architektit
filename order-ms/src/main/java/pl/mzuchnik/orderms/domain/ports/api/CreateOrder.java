package pl.mzuchnik.orderms.domain.ports.api;

import pl.mzuchnik.orderms.domain.BookId;
import pl.mzuchnik.orderms.domain.CustomerId;
import pl.mzuchnik.orderms.domain.Order;
import pl.mzuchnik.orderms.domain.OrderItem;

import java.util.List;

public interface CreateOrder {

    Order createOrder(List<BookId> books, CustomerId customerId);

}
