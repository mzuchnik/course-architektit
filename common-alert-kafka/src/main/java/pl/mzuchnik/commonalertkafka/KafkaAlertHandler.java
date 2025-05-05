package pl.mzuchnik.commonalertkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import pl.mzuchnik.commonalertmodel.Alert;

@FunctionalInterface
public interface KafkaAlertHandler {

    void handle(ConsumerRecord<String, Alert> record);
}
