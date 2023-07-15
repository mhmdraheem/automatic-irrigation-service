package com.banquemisr.irrigationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableRetry
public class AutomaticIrrigationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomaticIrrigationServiceApplication.class, args);
	}

}
