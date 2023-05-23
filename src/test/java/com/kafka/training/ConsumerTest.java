package com.kafka.training;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConsumerTest {

    private static Consumer consumer;

    @BeforeAll
    public static void createConsumer(){
        consumer = new Consumer();
    }

    @Test
    public void consumeRecords(){
        consumer.poll(Duration.ofSeconds(5));
        consumer.seekToBeginning();
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
        System.out.println("===== Consumed: " + records.count() + " records ====");
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("=============================");
            System.out.println(record.toString());
            System.out.println(record.timestamp());
            Instant instant = Instant.ofEpochMilli(record .timestamp());
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp= localDateTime.format(formatter);
            System.out.println("Formatted timestamp: " + formattedTimestamp);
        }

    }

    @AfterAll
    public static void tearDown(){
        consumer.close();
    }
}
