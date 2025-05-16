package pl.mzuchnik.commoncpumetricsmodel;

public record CpuMetric(
        double cpuProcentUsage,
        String applicationName,
        String instance,
        long timestamp
) {
}
