package pl.mzuchnik.commonkafkacpumetrics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import pl.mzuchnik.commoncpumetricsmodel.RamMetric;
import pl.mzuchnik.commoninstance.ServiceProperties;

import java.text.DecimalFormat;
import java.time.Instant;

@RequiredArgsConstructor
@Slf4j
class KafkaRamMetricEmitter {

    private final KafkaTemplate<String, RamMetric> producer;
    private final MetricsProperties properties;
    private final ServiceProperties serviceProperties;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Scheduled(fixedDelayString = "${metrics.ram.interval}")
    void emitRamMetrics() {
        RamMonitorService.RamStatistic ramStatistic = new RamMonitorService().getRamStatistic();

        RamMetric ramMetric = new RamMetric(
                toMB(ramStatistic.used()),
                toMB(ramStatistic.total()),
                toMB(ramStatistic.free()),
                serviceProperties.name(),
                serviceProperties.instance(),
                Instant.now().toEpochMilli());
        String topic = properties.ram().kafka().topic();
        log.trace("Sending ram metric event, topic: '{}', event: '{}'", topic, ramMetric);
        producer.send(topic, ramMetric);
    }

    private double toMB(long bytes) {
        return Double.parseDouble(decimalFormat.format((double) bytes / 1024 / 1024));

    }
}
