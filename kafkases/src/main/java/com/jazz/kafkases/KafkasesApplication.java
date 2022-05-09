package com.jazz.kafkases;

import java.util.concurrent.ExecutionException;

import com.jazz.kafkases.services.kafka.KafkaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkasesApplication {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(KafkasesApplication.class, args);
		String grupoId = System.getenv("KAFKA_GROUP_ID_READER");
		KafkaService.readMessage(grupoId);
	}
}