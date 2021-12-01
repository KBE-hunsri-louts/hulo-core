package com.huloteam.kbe;

import com.huloteam.kbe.odm.MongoDatastore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application
 * @author Spring Boot
 */
@SpringBootApplication
public class KbeApplication {

	private static final MongoDatastore MONGO_DATASTORE = new MongoDatastore(false);

	public static void main(String[] args) {

		SpringApplication.run(KbeApplication.class, args);

	}

}
