package pl.mzuchnik.systemmetricsanalyzerms.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.mzuchnik.commonalertmodel.Alert;
import pl.mzuchnik.commoncpumetricsmodel.CpuMetric;

@Service
@Slf4j
class CpuMetricsKafkaListener {

    private final KafkaTemplate<String, Alert> kafkaTemplate;

    CpuMetricsKafkaListener(@Qualifier("alertKafkaTemplate") KafkaTemplate<String, Alert> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "cpu-metrics",
                   groupId = "system-metrics-analyzer-ms",
                   containerFactory = "cpuMetricKafkaListenerContainerFactory")
    public void handle(ConsumerRecord<String, CpuMetric> record) {
        AlertCpuService alertCpuService = new AlertCpuService();
        Alert alert = alertCpuService.handle(record.value());
        log.debug("Cpu metrics received: {}", record.value());
        if(alert == null) {
            return;
        }
        log.debug("Sending alert: {}", alert);
        kafkaTemplate.send("alerts", alert);
    }
}
