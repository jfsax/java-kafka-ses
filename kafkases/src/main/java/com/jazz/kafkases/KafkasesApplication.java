package com.jazz.kafkases;

import java.util.concurrent.ExecutionException;

import com.jazz.kafkases.services.KafkaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkasesApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(KafkasesApplication.class, args);
		System.out.println("Lendo mensagens ...");
		// var grupoId = System.getenv("KAFKA_GROUP_ID_READER");
		// System.out.println(grupoId);
		KafkaService.readMessage("test-consumer-group");
	}

}
