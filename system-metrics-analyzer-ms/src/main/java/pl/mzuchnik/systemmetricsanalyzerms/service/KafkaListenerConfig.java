package pl.mzuchnik.systemmetricsanalyzerms.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import pl.mzuchnik.commoncpumetricsmodel.CpuMetric;

import java.util.HashMap;
import java.util.Map;

@Configuration
class KafkaListenerConfig {

    @Bean
    ConsumerFactory<String, CpuMetric> cpuMetricConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "system-metrics-analyzer-ms");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CpuMetric.class));
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, CpuMetric> cpuMetricKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CpuMetric> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cpuMetricConsumerFactory());

        return factory;
    }
}
