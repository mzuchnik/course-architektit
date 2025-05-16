package pl.mzuchnik.commonkafkacpumetrics;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "metrics")
public record MetricsProperties(
        boolean enabled,
        CPUProps cpu,
        RAMProps ram
) {
    public record CPUProps(KafkaProps kafka,
                           int interval) {
        public CPUProps{
            interval = 5000;
        }
    }

    public record RAMProps(KafkaProps kafka,
                           int interval) {
        public RAMProps{
            interval = 5000;
        }
    }

    public record KafkaProps(String bootstrapServers,
                             String topic,
                             Map<String, Object> producer,
                             Map<String, Object> consumer) {
        public KafkaProps{
            producer = new HashMap<>();
            consumer = new HashMap<>();
        }
    }
}
