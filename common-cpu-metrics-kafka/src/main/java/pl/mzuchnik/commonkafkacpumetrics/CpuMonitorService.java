package pl.mzuchnik.commonkafkacpumetrics;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@Slf4j
class CpuMonitorService {

    private final CentralProcessor processor;
    private long[] prevTicks;

    public CpuMonitorService() {
        SystemInfo systemInfo = new SystemInfo();
        this.processor = systemInfo.getHardware().getProcessor();
    }

    @PostConstruct
    public void init() {
        this.prevTicks = processor.getSystemCpuLoadTicks();
    }

    public double calculateGlobalCpuUsage() {
        long[] currentTicks = processor.getSystemCpuLoadTicks();
        double cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100.0;
        this.prevTicks = currentTicks;
        return cpuUsage;
    }
}
