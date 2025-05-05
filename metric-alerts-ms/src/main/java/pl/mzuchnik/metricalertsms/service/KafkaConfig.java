package pl.mzuchnik.metricalertsms.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import pl.mzuchnik.commonalertkafka.KafkaAlertHandler;
import pl.mzuchnik.commonalertmodel.Alert;

@Configuration
class KafkaConfig {

    @Bean
    KafkaAlertHandler kafkaAlertHandler(RedisTemplate<String, Alert> redisTemplate) {
        return new KafkaAlertHandlerImpl(redisTemplate);
    }
}
