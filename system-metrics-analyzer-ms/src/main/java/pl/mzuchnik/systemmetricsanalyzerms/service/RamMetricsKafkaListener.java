package pl.mzuchnik.systemmetricsanalyzerms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.mzuchnik.commonalertmodel.Alert;
import pl.mzuchnik.commoncpumetricsmodel.RamMetric;

@Service
@Slf4j
class RamMetricsKafkaListener {

    private final KafkaTemplate<String, Alert> kafkaTemplate;
    private final AlertRamService alertRamService = new AlertRamService();

    RamMetricsKafkaListener(KafkaTemplate<String, Alert> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            containerFactory = "ramMetricKafkaListenerContainerFactory",
            groupId = "ram-metrics-analyzer-ms",
            topics = "ram-metrics"
    )
    void handle(RamMetric ramMetric) {
        Alert alert = alertRamService.handle(ramMetric);
        log.debug("Sending alert: {}", alert);
        kafkaTemplate.send("alerts", alert);
    }
}
