package pl.mzuchnik.orderms.domain;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID uuid) {
    public CustomerId {
        Objects.requireNonNull(uuid);
    }
}
