package com.orass.orassmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrassmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrassmailApplication.class, args);
	}

}
