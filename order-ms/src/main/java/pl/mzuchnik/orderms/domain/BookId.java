package pl.mzuchnik.orderms.domain;

import java.util.Objects;
import java.util.UUID;

public record BookId(
        UUID uuid
) {
    public BookId{
        Objects.requireNonNull(uuid);
    }
}
