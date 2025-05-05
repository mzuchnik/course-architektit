package pl.mzuchnik.commonkafkacpumetrics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import pl.mzuchnik.commoncpumetricsmodel.CpuMetric;

import java.time.Instant;

@RequiredArgsConstructor
@Slf4j
class KafkaCpuMetricEmitter {

    private final KafkaTemplate<String, CpuMetric> producer;
    private final CpuMonitorService cpuMonitorService;
    private final CpuMetricsProperties properties;

    @Scheduled(fixedRate = 5000)
    public void emitEvent() {
        double cpuUsage = cpuMonitorService.calculateGlobalCpuUsage();
        CpuMetric cpuMetric = new CpuMetric(cpuUsage, properties.applicationName(), properties.instanceName(), Instant.now());
        String topic = properties.kafka().topic();

        log.trace("Sending cpu metric event, topic: '{}', event: '{}'", topic, cpuMetric);
        producer.send(topic, cpuMetric);
    }
}
