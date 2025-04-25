package pl.mzuchnik.orderms.infrastructure.adapters;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
class OrderItemJpaCollection {

    @Column(nullable = false, name = "product_id")
    private UUID productUuid;

    @Column(nullable = false, name = "quantity")
    private int quantity;

    @Column(nullable = false, name = "unit_price")
    private double unitPrice;
}
