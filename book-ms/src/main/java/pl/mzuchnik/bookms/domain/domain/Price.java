package pl.mzuchnik.bookms.domain.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record Price(BigDecimal price) {
    public Price {
        Objects.requireNonNull(price);
        if(price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    public static Price create(BigDecimal price) {
        return new Price(price);
    }
}
