package com.jazz.kafkases.services.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.jazz.kafkases.services.aws.SESSender;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaService {
    public static void readMessage(String groupId) throws InterruptedException, ExecutionException {
        Properties properties = KafkaConfig.getProperties(groupId);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singletonList(System.getenv("KAFKA_TOPIC")));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("------------------------------------------");
                System.out.println(record);

                try {
                    SESSender.sendMessage(record.key(), record.value());
                } catch (AddressException e) {
                    System.out.println(e.getMessage() + "Insert a valid address.");
                    e.printStackTrace();
                } catch (MessagingException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

                System.out.println("Message processed.");
                System.out.println("------------------------------------------");
            }
        }
    }
}