package com.huloteam.kbe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.huloteam.kbe.model.Product;
import com.huloteam.kbe.odm.MongoDatastore;
import com.huloteam.kbe.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to test methods of several classes.
 */
@SpringBootTest
class KbeApplicationTests {

	private static final OpenStreetMapService OPEN_STREET_MAP_SERVICE = new OpenStreetMapServiceImpl();
	private static final NominatimService NOMINATIM_SERVICE = new NominatimServiceImpl();
	private static final MongoDatastore MONGO_DATASTORE = new MongoDatastore(true);
	private static final CalculatorService CALCULATOR_SERVICE = new CalculatorServiceImpl();

	private static final LocalDateTime NOW = LocalDateTime.now();
	private static final Product EASY_AQUA = new Product();

	@BeforeEach
	void init() {

		EASY_AQUA.setId(1);
		EASY_AQUA.setBrand("Melitta");
		EASY_AQUA.setProductName("1016-02 Easy Aqua");
		EASY_AQUA.setDescription("Schwarz");
		EASY_AQUA.setProvider("Provider A");
		EASY_AQUA.setProductTyp("Water boiler");
		EASY_AQUA.setLocation("Storage 1");
		EASY_AQUA.setAmount(6);
		EASY_AQUA.setAvailableAmount(6);
		EASY_AQUA.setProviderPrice(1794);
		EASY_AQUA.setPriceWithoutTax(2097);
		EASY_AQUA.setStoredSince(NOW);

		EASY_AQUA.setId(2);
		EASY_AQUA.setBrand("Miele");
		EASY_AQUA.setProductName("WWE 360 WPS");
		EASY_AQUA.setDescription("Weiss");
		EASY_AQUA.setProvider("Miele");
		EASY_AQUA.setProductTyp("Washing machine");
		EASY_AQUA.setLocation("Storage 2");
		EASY_AQUA.setAmount(2);
		EASY_AQUA.setAvailableAmount(2);
		EASY_AQUA.setProviderPrice(39569);
		EASY_AQUA.setPriceWithoutTax(52857);
		EASY_AQUA.setStoredSince(NOW);

		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);

		NOMINATIM_SERVICE.startApi("bogenstrasse1", "hoppegarten", "15366");
		OPEN_STREET_MAP_SERVICE.startApi("bogenstrasse1", "hoppegarten", "15366");

	}

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

	@Test
	@DisplayName("OpenStreetMapService LonLat")
	void getLonLatOfOpenStreetMapService() {

		assertEquals(14.473361149999999, NOMINATIM_SERVICE.getToLon());
		assertEquals(51.60227345, NOMINATIM_SERVICE.getToLat());
		// assertEquals("", NOMINATIM_SERVICE.getLonLatResponse());

	}

	@Test
	@DisplayName("OpenStreetMapService duration")
	void getDurationOfOpenStreetMapService() {

		assertEquals(14.549448333333334, OPEN_STREET_MAP_SERVICE.getDuration());
		// assertEquals("", OPEN_STREET_MAP_SERVICE.getDurationResponse());

	}

	@Test
	@DisplayName("CalculationService calculatedPrice")
	void getCalculatedPrice() {

		// Can just be tested if the calculator is online.
		assertEquals(24.95, CALCULATOR_SERVICE.getPriceWithTax("DE_REGULAR", "20.97"));

	}

}