package pl.mzuchnik.commonkafkacpumetrics;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "metrics.cpu")
public record CpuMetricsProperties(
        boolean enabled,
        String applicationName,
        String instanceName,
        CPUKafkaProducerMetrics kafka
) {
    public record CPUKafkaProducerMetrics(
            List<String> bootstrapServers,
            String topic,
            Acts acts
    ){}

    public enum Acts{
        NO_CONFIRMATION("0"),
        ONLY_LEADER_CONFIRMATION("1"),
        IN_SYNC_CONFIRMATION("all");

        private final String ack;

        Acts(String ack){
            this.ack = ack;
        }

        public String getAck() {
            return ack;
        }
    }
}
