package pl.mzuchnik.commonkafkacpumetrics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import pl.mzuchnik.commoncpumetricsmodel.CpuMetric;
import pl.mzuchnik.commoninstance.ServiceProperties;

import java.text.DecimalFormat;
import java.time.Instant;

@RequiredArgsConstructor
@Slf4j
class KafkaCpuMetricEmitter {

    private final KafkaTemplate<String, CpuMetric> producer;
    private final CpuMonitorService cpuMonitorService;
    private final MetricsProperties properties;
    private final ServiceProperties serviceProperties;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Scheduled(fixedDelayString = "${metrics.cpu.interval}")
    public void emitCPUMetricEvent() {
        double cpuUsage = cpuMonitorService.calculateGlobalCpuUsage();
        CpuMetric cpuMetric = new CpuMetric(Double.parseDouble(decimalFormat.format(cpuUsage)), serviceProperties.name(), serviceProperties.instance(), Instant.now().toEpochMilli());
        String topic = properties.cpu().kafka().topic();
        log.trace("Sending cpu metric event, topic: '{}', event: '{}'", topic, cpuMetric);
        producer.send(topic, cpuMetric);
    }
}
