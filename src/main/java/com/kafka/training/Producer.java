package com.kafka.training;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public class Producer {
    KafkaProducer<String, String> producer;

    Producer() {
        producer = new KafkaProducer<>(KafkaProperties.producerProperties());
    }

    public Future<RecordMetadata> send(ProducerRecord<String, String> record) {
        return producer.send(record);
    }

    public void close() {
        producer.close();
    }

}
