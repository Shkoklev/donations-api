package com.mk.donations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DonationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonationsApplication.class, args);
	}

}

