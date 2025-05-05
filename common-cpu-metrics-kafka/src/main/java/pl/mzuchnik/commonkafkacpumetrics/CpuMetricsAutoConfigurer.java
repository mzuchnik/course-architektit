package pl.mzuchnik.commonkafkacpumetrics;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.mzuchnik.commoncpumetricsmodel.CpuMetric;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(CpuMetricsProperties.class)
@EnableScheduling
@Slf4j
class CpuMetricsAutoConfigurer {

    @Bean
    public ProducerFactory<String, CpuMetric> getProducerCpuMetricConfig(CpuMetricsProperties cpuMetricsProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, cpuMetricsProperties.kafka().acts().getAck());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, cpuMetricsProperties.kafka().bootstrapServers());
        JsonSerializer<CpuMetric> jsonSerializer = new JsonSerializer<>();

        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), jsonSerializer);
    }

    @Bean
    public KafkaTemplate<String, CpuMetric> metricsKafkaTemplate(CpuMetricsProperties cpuMetricsProperties) {
        return new KafkaTemplate<>(getProducerCpuMetricConfig(cpuMetricsProperties));
    }

    @Bean
    public CpuMonitorService cpuMonitorService() {
        CpuMonitorService cpuMonitorService = new CpuMonitorService();
        cpuMonitorService.init();
        return cpuMonitorService;
    }

    @Bean
    public KafkaCpuMetricEmitter kafkaCpuMetricProducer(@Qualifier("metricsKafkaTemplate") KafkaTemplate<String, CpuMetric> kafkaTemplate,
                                                        CpuMonitorService cpuMonitorService,
                                                        CpuMetricsProperties cpuMetricsProperties) {
        return new KafkaCpuMetricEmitter(kafkaTemplate, cpuMonitorService, cpuMetricsProperties);
    }
}
