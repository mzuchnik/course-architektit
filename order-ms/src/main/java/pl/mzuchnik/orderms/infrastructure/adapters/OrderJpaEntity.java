package pl.mzuchnik.orderms.infrastructure.adapters;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
class OrderJpaEntity {

    @Id
    private UUID uuid;

    @Column(nullable = false, name = "customer_id")
    private UUID customerUuid;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_items", joinColumns = {@JoinColumn(name = "order_uuid")})
    @AttributeOverrides(value = {
            @AttributeOverride(name = "productUuid", column = @Column(name = "product_uuid")),
            @AttributeOverride(name = "unitPrice", column = @Column(name = "unit_price"))
    })
    Set<OrderItemJpaCollection> orderItems;

    @Column(nullable = false, name = "create_at")
    private LocalDateTime createdAt;
}
