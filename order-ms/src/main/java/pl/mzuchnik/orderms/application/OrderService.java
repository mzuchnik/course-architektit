package pl.mzuchnik.orderms.application;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import pl.mzuchnik.orderms.domain.*;
import pl.mzuchnik.orderms.domain.ports.api.CreateOrder;
import pl.mzuchnik.orderms.domain.ports.spi.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderService implements CreateOrder {

    private final OrderRepository orderRepository;
    private final BookPriceProvider bookPriceProvider;

    @Override
    public Order createOrder(List<BookId> bookIds, CustomerId customerId) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (BookId bookId : bookIds) {
            Price price = bookPriceProvider.getBookPrice(bookId).
                    orElseThrow(() -> new OrderException("Cannot find price for BookId: " + bookId.uuid()));
            orderItems.add(new OrderItem(bookId, 1, price));
        }

        Order newOrder = OrderFactory.createNewOrder(orderItems, customerId);
        orderRepository.createOrder(newOrder);

        //TODO: Emit event etc NewOrderCreatedEvent(OrderId)

        return newOrder;
    }
}
