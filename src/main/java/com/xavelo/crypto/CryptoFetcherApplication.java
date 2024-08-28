package com.xavelo.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:git.properties")
public class CryptoFetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoFetcherApplication.class, args);
	}

}
