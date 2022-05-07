package com.jazz.kafkases.services;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaService {
    public static void readMessage(String groupId) throws InterruptedException, ExecutionException {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties(groupId));
        consumer.subscribe(Collections.singletonList(System.getenv("KAFKA_TOPIC")));

        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> registro : records) {
                System.out.println("------------------------------------------");
                System.out.println("Partition: " + registro.partition());
                System.out.println("offset: " + registro.offset());

                System.out.println(registro.key() + ": " + registro.value());

                System.out.println("Message processed.");
                System.out.println("------------------------------------------");
            }
        }
    }

    private static Properties properties(String groupId) {
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKA_HOST"));
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return properties;
    }
}