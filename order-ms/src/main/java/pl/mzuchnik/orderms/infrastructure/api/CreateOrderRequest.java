package pl.mzuchnik.orderms.infrastructure.api;

import java.util.List;
import java.util.UUID;

record CreateOrderRequest(
        List<UUID> productUuid,
        UUID customerUuid
) {
}
