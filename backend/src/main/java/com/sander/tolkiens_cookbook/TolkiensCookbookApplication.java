package com.sander.tolkiens_cookbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for this application.
 * <p>
 * This class bootstraps the application by initializing the Spring Application Context
 * and starting the embedded web server.
 * </p>
 */
@SpringBootApplication
public class TolkiensCookbookApplication {

	/**
	 * Main method to launch the Spring Boot application.
	 *
	 * @param args Command-line arguments passed during application startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TolkiensCookbookApplication.class, args);
	}
}
