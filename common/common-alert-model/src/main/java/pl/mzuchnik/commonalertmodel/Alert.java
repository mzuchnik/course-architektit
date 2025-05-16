package pl.mzuchnik.commonalertmodel;

public record Alert(
        String type,
        String priority,
        String application,
        String instance,
        String value,
        long timestamp
) {
}
