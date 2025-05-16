package pl.mzuchnik.systemmetricsanalyzerms.service;

import pl.mzuchnik.commonalertmodel.Alert;
import pl.mzuchnik.commoncpumetricsmodel.CpuMetric;

import java.time.Instant;

class AlertCpuService {

    public Alert handle(CpuMetric cpuMetric) {
        if (cpuMetric == null) {
            return null;
        }
        double cpuUsage = cpuMetric.cpuProcentUsage();

        if(cpuUsage < 30){
            return null;
        }

        String priority = "LOW";

        if(cpuUsage >= 85.0){
            priority = "HIGH";
        }
        else if(cpuUsage >= 50.0){
            priority = "MEDIUM";
        }

        return new Alert("CPU", priority, cpuMetric.applicationName(), cpuMetric.instance(), String.valueOf(cpuMetric.cpuProcentUsage()), Instant.now().toEpochMilli());
    }
}
