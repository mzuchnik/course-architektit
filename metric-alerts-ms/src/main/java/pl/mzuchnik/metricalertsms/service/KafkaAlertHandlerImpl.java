package pl.mzuchnik.metricalertsms.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import pl.mzuchnik.commonalertkafka.KafkaAlertHandler;
import pl.mzuchnik.commonalertmodel.Alert;
import pl.mzuchnik.metricalertsms.model.AlertEntity;
import pl.mzuchnik.metricalertsms.repository.AlertEntityRepository;

import java.time.Duration;

public class KafkaAlertHandlerImpl implements KafkaAlertHandler {

    private final AlertEntityRepository alertEntityRepository;
    private final long TIME_TO_LIVE = Duration.ofDays(1).toSeconds();

    public KafkaAlertHandlerImpl(AlertEntityRepository alertEntityRepository) {
        this.alertEntityRepository = alertEntityRepository;
    }

    @Override
    public void handle(ConsumerRecord<String, Alert> record) {
        AlertEntity alertEntity = mapToEntity(record);
        alertEntityRepository.save(alertEntity);
    }

    private AlertEntity mapToEntity(ConsumerRecord<String, Alert> record) {
        return AlertEntity.of(buildKey(record.topic(), record.partition(), record.offset()),
                record.value(),
                TIME_TO_LIVE);
    }

    private static String buildKey(String topic, int partition, long offset) {
        return String.format("%s:%d:%d", topic, partition, offset);
    }
}
