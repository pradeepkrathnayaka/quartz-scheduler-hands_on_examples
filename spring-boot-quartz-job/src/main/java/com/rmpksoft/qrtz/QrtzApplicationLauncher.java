package com.rmpksoft.qrtz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QrtzApplicationLauncher {
	
	private static final Logger log = LoggerFactory.getLogger(QrtzApplicationLauncher.class);

	public static void main(String[] args) {
		log.info("Start Quartz Application");
		SpringApplication.run(QrtzApplicationLauncher.class, args);
	}

}
