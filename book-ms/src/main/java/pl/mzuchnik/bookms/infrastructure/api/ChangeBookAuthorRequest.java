package pl.mzuchnik.bookms.infrastructure.api;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
record ChangeBookAuthorRequest(
        @NotBlank(message = "Author is mandatory") String author) {
}
