package pl.mzuchnik.commoncpumetricsmodel;

public record RamMetric(
        double currentUsage,
        double totalRam,
        double freeRam,
        String applicationName,
        String instance,
        long timestamp
) {
}
