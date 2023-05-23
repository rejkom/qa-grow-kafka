package com.kafka.training;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;


public class ProducerTest {
    private static Producer producer;

    @BeforeAll
    public static void createProducer(){
        producer = new Producer();
    }

    @Test
    public void produceRecords() throws ExecutionException, InterruptedException {
        RecordMetadata metadata = producer.send(new ProducerRecord<>(KafkaProperties.TOPIC, "Hello World")).get();
    }

    @AfterAll
    public static void tearDown(){
        producer.close();
    }

}
