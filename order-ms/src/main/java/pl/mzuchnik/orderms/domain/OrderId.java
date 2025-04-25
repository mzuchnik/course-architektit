package pl.mzuchnik.orderms.domain;

import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID uuid) {
    public OrderId {
        Objects.requireNonNull(uuid);
    }

    public static OrderId createNew() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId create(UUID uuid) {
        return new OrderId(uuid);
    }
}
