package pl.mzuchnik.orderms.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import pl.mzuchnik.orderms.application.OrderService;
import pl.mzuchnik.orderms.domain.BookId;
import pl.mzuchnik.orderms.domain.CustomerId;
import pl.mzuchnik.orderms.domain.Order;
import pl.mzuchnik.orderms.domain.OrderId;
import pl.mzuchnik.orderms.domain.ports.spi.OrderRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
class OrderApi {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping
    List<OrderResponse> getAllOrders() {
        return orderRepository.getAllOrders().stream().map(OrderResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    OrderResponse getOrder(@PathVariable(name = "uuid") String uuid) {
        return OrderResponse.from(orderRepository.getOrderById(OrderId.create(UUID.fromString(uuid))));
    }

    @PostMapping
    OrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest){

        Order order = orderService.createOrder(
                createOrderRequest.productUuid().stream().map(BookId::new).toList(),
                new CustomerId(createOrderRequest.customerUuid()));

        return OrderResponse.from(order);
    }

}
