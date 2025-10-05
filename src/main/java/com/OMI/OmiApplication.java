package com.OMI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OmiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmiApplication.class, args);
	}

}
