package pl.mzuchnik.commonkafkacpumetrics;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

class RamMonitorService {

    RamStatistic getRamStatistic() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long totalRAM = osBean.getTotalMemorySize();
        long freeRAM = osBean.getFreeMemorySize();
        long usedRAM = totalRAM - freeRAM;

        return new RamStatistic(totalRAM, freeRAM, usedRAM);
    }

    public record RamStatistic(long total, long free, long used) {

    }
}
