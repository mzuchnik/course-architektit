package pl.mzuchnik.orderms.infrastructure.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mzuchnik.orderms.domain.Order;
import pl.mzuchnik.orderms.domain.OrderId;
import pl.mzuchnik.orderms.domain.OrderNotFoundException;
import pl.mzuchnik.orderms.domain.ports.spi.OrderRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaEntityRepository orderJpaEntityRepository;

    @Override
    public Order createOrder(Order order) {
         orderJpaEntityRepository.save(OrderEntityMapper.toJpaEntity(order));
         return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderJpaEntityRepository.findAll().stream()
                .map(OrderEntityMapper::toDomainEntity).toList();
    }

    @Override
    public Order getOrderById(OrderId id) throws OrderNotFoundException {
        return orderJpaEntityRepository.findById(id.uuid())
                .map(OrderEntityMapper::toDomainEntity).orElseThrow(
                        () -> new OrderNotFoundException(id + " not found"));
    }
}
