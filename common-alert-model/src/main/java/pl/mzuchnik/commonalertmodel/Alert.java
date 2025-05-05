package pl.mzuchnik.commonalertmodel;

import java.time.Instant;

public record Alert(
        String type,
        String priority,
        String message,
        Instant time
) {
}
