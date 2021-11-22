package com.huloteam.kbe;

import com.huloteam.kbe.odm.MongoDatastore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KbeApplication {

	private final MongoDatastore mongoDatastore = new MongoDatastore();

	public static void main(String[] args) {

		SpringApplication.run(KbeApplication.class, args);

	}

}
