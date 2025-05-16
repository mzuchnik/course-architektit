package pl.mzuchnik.metricalertsms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mzuchnik.commonalertkafka.KafkaAlertHandler;
import pl.mzuchnik.metricalertsms.repository.AlertEntityRepository;
import pl.mzuchnik.metricalertsms.service.KafkaAlertHandlerImpl;

@Configuration
class KafkaConfig {

    @Bean
    KafkaAlertHandler kafkaAlertHandler(AlertEntityRepository alertEntityRepository) {
        return new KafkaAlertHandlerImpl(alertEntityRepository);
    }
}
