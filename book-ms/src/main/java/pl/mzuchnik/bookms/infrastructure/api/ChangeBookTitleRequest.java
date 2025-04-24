package pl.mzuchnik.bookms.infrastructure.api;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
record ChangeBookTitleRequest(
        @NotBlank(message = "Title is mandatory") String title) {
}
