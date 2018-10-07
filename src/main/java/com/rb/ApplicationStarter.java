package com.rb;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationStarter {

	private static final Logger log = LogManager
			.getLogger(ApplicationStarter.class);

	public static void main(String[] args) throws Exception {
		log.info("application started");
		SpringApplication.run(ApplicationStarter.class, args);
	}
}