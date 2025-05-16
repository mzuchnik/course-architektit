package pl.mzuchnik.commonkafkacpumetrics;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.mzuchnik.commoncpumetricsmodel.CpuMetric;
import pl.mzuchnik.commoncpumetricsmodel.RamMetric;
import pl.mzuchnik.commoninstance.ServiceProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(MetricsProperties.class)
@ConditionalOnProperty(prefix = "metrics", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableScheduling
public class MetricsAutoConfigurer {

    @Bean
    ProducerFactory<String, CpuMetric> getProducerCpuMetricConfig(MetricsProperties props) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.cpu().kafka().bootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.putAll(props.cpu().kafka().producer());
        JsonSerializer<CpuMetric> jsonSerializer = new JsonSerializer<>();

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), jsonSerializer);
    }

    @Bean("metricsCPUKafkaTemplate")
    @ConditionalOnMissingBean(name = "metricsCPUKafkaTemplate")
    KafkaTemplate<String, CpuMetric> metricsCPUKafkaTemplate(MetricsProperties metricsProperties) {
        return new KafkaTemplate<>(getProducerCpuMetricConfig(metricsProperties));
    }

    @Bean
    @ConditionalOnMissingBean
    KafkaCpuMetricEmitter kafkaCpuMetricProducer(MetricsProperties metricsProperties,
                                                 ServiceProperties serviceProperties) {
        CpuMonitorService cpuMonitorService = new CpuMonitorService();
        cpuMonitorService.init();
        return new KafkaCpuMetricEmitter(
                metricsCPUKafkaTemplate(metricsProperties),
                cpuMonitorService,
                metricsProperties,
                serviceProperties);
    }

    @Bean
    ProducerFactory<String, RamMetric> getProducerRamMetricConfig(MetricsProperties props) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.ram().kafka().bootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.putAll(props.ram().kafka().producer());

        JsonSerializer<RamMetric> jsonSerializer = new JsonSerializer<>();

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), jsonSerializer);
    }

    @Bean("metricsRamKafkaTemplate")
    @ConditionalOnMissingBean(name = "metricsRamKafkaTemplate")
    KafkaTemplate<String, RamMetric> metricsRamKafkaTemplate(MetricsProperties props) {
        return new KafkaTemplate<>(getProducerRamMetricConfig(props));
    }

    @Bean
    @ConditionalOnMissingBean
    KafkaRamMetricEmitter kafkaRamMetricEmitter(
            @Qualifier("metricsRamKafkaTemplate") KafkaTemplate<String, RamMetric> metricsRamKafkaTemplate,
            MetricsProperties props,
            ServiceProperties serviceProperties) {

        return new KafkaRamMetricEmitter(metricsRamKafkaTemplate, props, serviceProperties);
    }
}
