package pl.mzuchnik.metricalertsms.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import pl.mzuchnik.commonalertkafka.KafkaAlertHandler;
import pl.mzuchnik.commonalertmodel.Alert;

import java.time.Duration;

class KafkaAlertHandlerImpl implements KafkaAlertHandler {

    private final RedisTemplate<String, Alert> redisTemplate;

    KafkaAlertHandlerImpl(RedisTemplate<String, Alert> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void handle(ConsumerRecord<String, Alert> record) {
        Alert alert = record.value();
        redisTemplate.opsForValue()
                .setIfAbsent(
                        buildKey(
                                record.topic(),
                                record.partition(),
                                record.offset()),
                        alert,
                        Duration.ofHours(24));
    }

    private static String buildKey(String topic, int partition, long offset){
        return String.format("%s:%d:%d", topic, partition, offset);
    }
}
