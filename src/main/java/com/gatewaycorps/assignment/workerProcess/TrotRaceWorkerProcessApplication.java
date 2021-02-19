package com.gatewaycorps.assignment.workerProcess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TrotRaceWorkerProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrotRaceWorkerProcessApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
}
