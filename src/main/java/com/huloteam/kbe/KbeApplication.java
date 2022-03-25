package com.huloteam.kbe;

import com.huloteam.kbe.csv.CSVExporter;
import com.huloteam.kbe.csv.CSVExporterImpl;
import com.huloteam.kbe.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

/**
 * Main Application
 * @author Spring Boot
 */
@SpringBootApplication
public class KbeApplication {
	private static final CSVExporter CSV_EXPORTER = new CSVExporterImpl();
	private static final Product EASY_AQUA = new Product();

	public static void main(String[] args) {
		SpringApplication.run(KbeApplication.class, args);

		EASY_AQUA.setProductName("1016-02 Easy Aqua");
		EASY_AQUA.setProvider("Provider 1");
		EASY_AQUA.setProviderPrice(1794);
		EASY_AQUA.setStoredSince(LocalDateTime.now());

		CSV_EXPORTER.createCSV(EASY_AQUA);
	}
}
