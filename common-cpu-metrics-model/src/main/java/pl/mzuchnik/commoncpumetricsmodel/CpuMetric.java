package pl.mzuchnik.commoncpumetricsmodel;

import java.time.Instant;

public record CpuMetric(
        double cpuProcentUsage,
        String applicationName,
        String instance,
        Instant time
) {
}
