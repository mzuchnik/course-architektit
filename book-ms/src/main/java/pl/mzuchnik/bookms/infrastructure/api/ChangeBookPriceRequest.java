package pl.mzuchnik.bookms.infrastructure.api;

import org.springframework.validation.annotation.Validated;

@Validated
record ChangeBookPriceRequest(double price) {
}
