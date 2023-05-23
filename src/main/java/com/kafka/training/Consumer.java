package com.kafka.training;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.List;

public class Consumer {
    KafkaConsumer<String, String> consumer;
    Consumer() {
        consumer = new KafkaConsumer<>(KafkaProperties.consumerProperties());
        consumer.subscribe(List.of("qa-grow"));
    }
    public ConsumerRecords<String, String> poll(Duration timeout){
        return consumer.poll(timeout);
    }

    public void seekToBeginning(){
        consumer.seekToBeginning(consumer.assignment());
    }

    public void close() {
        consumer.close();
    }
}
