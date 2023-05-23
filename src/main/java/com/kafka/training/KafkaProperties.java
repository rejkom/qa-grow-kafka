package com.kafka.training;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProperties {
public static final String TOPIC = "qa-grow";
    public static Properties consumerProperties(){
        Properties props = new Properties();
        props.putAll(commonProperties());
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        return props;
    }

    public static Properties producerProperties(){
        Properties props = new Properties();
        props.putAll(commonProperties());
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        return props;
    }

    private static Properties commonProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.122.68:9092");
        props.put("group.id", "qa-grow-jena");
        return props;
    }

}
