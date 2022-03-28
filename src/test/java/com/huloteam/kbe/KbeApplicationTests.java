package com.huloteam.kbe;

import com.huloteam.kbe.csv.CSVExporter;
import com.huloteam.kbe.csv.CSVExporterImpl;
import com.huloteam.kbe.model.Product;
import com.huloteam.kbe.odm.MongoDatastore;
import com.huloteam.kbe.service.*;
import com.huloteam.kbe.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test methods of several classes.
 */
@SpringBootTest
class KbeApplicationTests {
	private static final OpenStreetMapService OPEN_STREET_MAP_SERVICE = new OpenStreetMapServiceImpl();
	private static final NominatimService NOMINATIM_SERVICE = new NominatimServiceImpl();
	private static final MongoDatastore MONGO_DATASTORE = new MongoDatastore(true);
	private static final CalculatorService CALCULATOR_SERVICE = new CalculatorServiceImpl();
	private static final StorageService STORAGE_SERVICE = new StorageServiceImpl();

	private static final Product EASY_AQUA = new Product();
	private static final Product WWE_360_WPS = new Product();

	private static final double LON = 13.411678737741736;
	private static final double LAT = 52.5680912;
	private static final double DURATION = 0;

	@BeforeEach
	void init() {
		EASY_AQUA.setId(1);
		EASY_AQUA.setBrand("Melitta");
		EASY_AQUA.setProductName("1016-02 Easy Aqua");
		EASY_AQUA.setDescription("Schwarz");
		EASY_AQUA.setProductTyp("Water boiler");
		EASY_AQUA.setLocation("Storage 1");
		EASY_AQUA.setAmount(6);
		EASY_AQUA.setAvailableAmount(6);
		EASY_AQUA.setPriceWithoutTax(2097);

		WWE_360_WPS.setId(2);
		WWE_360_WPS.setBrand("Miele");
		WWE_360_WPS.setProductName("WWE 360 WPS");
		WWE_360_WPS.setDescription("Weiss");
		WWE_360_WPS.setProductTyp("Washing machine");
		WWE_360_WPS.setLocation("Storage 2");
		WWE_360_WPS.setAmount(2);
		WWE_360_WPS.setAvailableAmount(2);
		WWE_360_WPS.setPriceWithoutTax(52857);

		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);
		MONGO_DATASTORE.saveIntoMongo(WWE_360_WPS);

		NOMINATIM_SERVICE.startApi("berlinerstrasse","13", "berlin", "13187");
		OPEN_STREET_MAP_SERVICE.startApi("berlinerstrasse", "13", "berlin", "13187");
	}

	// database tests

	@Test
	@DisplayName("MongoDB put")
	void putIntoMongoDatabase() {
		List<Product> productList = MONGO_DATASTORE.queryFromMongo(Product.PRODUCT_NAME, "1016-02 Easy Aqua");

		assertEquals("Melitta", productList.get(0).getBrand());
		assertEquals("1016-02 Easy Aqua", productList.get(0).getProductName());
	}

	@Test
	@DisplayName("MongoDB delete")
	void deleteDataMongoDatabase() {
		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);
		MONGO_DATASTORE.deleteProductInMongo(Product.PRODUCT_NAME, "1016-02 Easy Aqua");

		List<Product> productList1 = MONGO_DATASTORE.queryFromMongo(Product.PRODUCT_NAME, EASY_AQUA.getBrand());
		List<Product> productList2 = new LinkedList<>();

		assertEquals(productList1, productList2);
	}

	@Test
	@DisplayName("MongoDB update Integer")
	void updateAnIntegerDataMongoDatabase() {
		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);
		MONGO_DATASTORE.updateIntoMongo(
				Product.PRODUCT_NAME,
				"1016-02 Easy Aqua",
				Product.AMOUNT,
				"-4");

		assertEquals(2, MONGO_DATASTORE.queryFromMongo(Product.BRAND, "Melitta").get(0).getAmount());
	}

	@Test
	@DisplayName("MongoDB update String")
	void updateAnStringDataMongoDatabase() {
		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);
		MONGO_DATASTORE.updateIntoMongo(
				Product.PRODUCT_NAME,
				"1016-02 Easy Aqua",
				Product.BRAND,
				"Miele");

		assertEquals("Miele", MONGO_DATASTORE.queryFromMongo(Product.PRODUCT_NAME, "1016-02 Easy Aqua").get(0).getBrand());
	}

	@Test
	@DisplayName("MongoDB update String with numbers")
	void updateAnStringWithNumbersMongoDatabase() {
		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);
		MONGO_DATASTORE.updateIntoMongo(
				Product.PRODUCT_NAME,
				"1016-02 Easy Aqua",
				Product.PRODUCT_NAME,
				"1016-03 Easy Aqua");

		assertEquals("1016-03 Easy Aqua", MONGO_DATASTORE.queryFromMongo(Product.PRODUCT_NAME, "1016-03 Easy Aqua").get(0).getProductName());
	}

	@Test
	@DisplayName("MongoDB get all Products")
	void getAllFromMongo() {
		List<Product> productList = MONGO_DATASTORE.queryAllFromMongo();

		assertEquals(2, productList.size());
	}

	// external API test

	@Test
	@DisplayName("OpenStreetMapService LonLat")
	void getLonLatOfOpenStreetMapService() {
		assertEquals(LON, NOMINATIM_SERVICE.getToLon());
		assertEquals(LAT, NOMINATIM_SERVICE.getToLat());
		// assertEquals("", NOMINATIM_SERVICE.getLonLatResponse());
	}

	@Test
	@DisplayName("OpenStreetMapService duration")
	void getDurationOfOpenStreetMapService() {
		assertEquals(DURATION, OPEN_STREET_MAP_SERVICE.getDuration());
		// assertEquals("", OPEN_STREET_MAP_SERVICE.getDurationResponse());
	}

	// internal API tests

	@Test
	@DisplayName("CalculationService calculatedPrice")
	void getCalculatedPrice() {
		// Can just be tested if the calculator is online.
		assertEquals(24.95, CALCULATOR_SERVICE.getPriceWithTax("DE_REGULAR", "20.97"));
	}

	@Test
	@DisplayName("StorageService productInformation")
	void getStorageProductInformation() {
		// Can just be tested if the storage is online.
		assertEquals(EASY_AQUA.getProvider(), STORAGE_SERVICE.getStorageProductInformation(EASY_AQUA.getProductName()));
	}

	// Validator test

	@Test
	@DisplayName("Validator only numbers: 142a is false")
	void isStringOnlyContainingNumberFalse() {
		assertFalse(Validator.isStringOnlyContainingNumbers("142a"));
	}

	@Test
	@DisplayName("Validator only numbers: 142 is true")
	void isStringOnlyContainingNumberTrue() {
		assertTrue(Validator.isStringOnlyContainingNumbers("142"));
	}

	@Test
	@DisplayName("Validator number comparator: 13 > 14 is false")
	void isFirstNumberBiggerFalse() {
		assertFalse(Validator.isNumberBiggerLowerComparison(13, 14));
	}

	@Test
	@DisplayName("Validator number comparator: 14 > 13 is true")
	void isFirstNumberBiggerTrue() {
		assertTrue(Validator.isNumberBiggerLowerComparison(14, 13));
	}

	@Test
	@DisplayName("Validator string contains numbers: Dudu is false")
	void isStringContainingNumbersFalse() {
		assertFalse(Validator.isStringContainingNumbers("Dudu"));
	}

	@Test
	@DisplayName("Validator string contains numbers: Du42du is true")
	void isStringContainingNumbersTrue() {
		assertTrue(Validator.isStringContainingNumbers("Du42du"));
	}
}