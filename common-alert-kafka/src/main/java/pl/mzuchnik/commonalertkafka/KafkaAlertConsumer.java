package pl.mzuchnik.commonalertkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import pl.mzuchnik.commonalertmodel.Alert;

class KafkaAlertConsumer {

    private final KafkaAlertHandler kafkaAlertHandler;

    KafkaAlertConsumer(KafkaAlertHandler kafkaAlertHandler) {
        this.kafkaAlertHandler = kafkaAlertHandler;
    }

    @KafkaListener(
            topics = "alerts",
            groupId = "alert-consumer-ms",
            containerFactory = "kafkaAlertContainerFactory")
    public void handle(ConsumerRecord<String, Alert> record) {
        kafkaAlertHandler.handle(record);
    }
}
