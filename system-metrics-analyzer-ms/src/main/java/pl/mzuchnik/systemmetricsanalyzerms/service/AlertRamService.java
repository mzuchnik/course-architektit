package pl.mzuchnik.systemmetricsanalyzerms.service;

import pl.mzuchnik.commonalertmodel.Alert;
import pl.mzuchnik.commoncpumetricsmodel.RamMetric;

import java.time.Instant;

class AlertRamService {

    public Alert handle(RamMetric ramMetric){

        return new Alert("RAM", "NORMAL", ramMetric.applicationName(), ramMetric.instance(), calcValue(ramMetric), Instant.now().toEpochMilli());
    }

    private String calcValue(RamMetric ramMetric){
        return String.valueOf((ramMetric.currentUsage() / ramMetric.totalRam()) *100.0);
    }
}
