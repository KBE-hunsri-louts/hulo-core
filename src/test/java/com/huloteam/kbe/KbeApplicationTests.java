package com.huloteam.kbe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.huloteam.kbe.model.Product;
import com.huloteam.kbe.odm.MongoDatastore;
import com.huloteam.kbe.service.NominatimService;
import com.huloteam.kbe.service.OpenStreetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class KbeApplicationTests {

	@Autowired
	private static final OpenStreetMapService OPEN_STREET_MAP_SERVICE = new OpenStreetMapService();
	private static final NominatimService NOMINATIM_SERVICE = new NominatimService();
	private static final MongoDatastore MONGO_DATASTORE = new MongoDatastore(true);

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

		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);

		NOMINATIM_SERVICE.startApi();
		OPEN_STREET_MAP_SERVICE.startApi();

	}

	/**
	 * GoodCase
	 */
	@Test
	@DisplayName("MongoDB put")
	void putIntoMongoDatabase() {

		List<Product> productList = MONGO_DATASTORE.queryFromMongo(Product.PRODUCT_NAME, "1016-02 Easy Aqua");

		// Should be: Melitta - Melitta
		assertEquals("Melitta", productList.get(0).getBrand());
		// Should be: 1016-02 Easy Aqua - 1016-02 Easy Aqua
		assertEquals("1016-02 Easy Aqua", productList.get(0).getProductName());

	}

	/**
	 * GoodCase
	 */
	@Test
	@DisplayName("MongoDB delete")
	void deleteDataMongoDatabase() {

		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);
		MONGO_DATASTORE.deleteProductInMongo(Product.PRODUCT_NAME, "1016-02 Easy Aqua");

		List<Product> productList1 = MONGO_DATASTORE.queryFromMongo(Product.PRODUCT_NAME, EASY_AQUA.getBrand());
		List<Product> productList2 = new LinkedList<>();

		// Should be: [] - []
		assertEquals(productList1, productList2);

	}

	/**
	 * GoodCase
	 */
	@Test
	@DisplayName("MongoDB update Integer")
	void updateAnIntegerDataMongoDatabase() {

		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);
		MONGO_DATASTORE.updateIntoMongo(
				Product.PRODUCT_NAME,
				"1016-02 Easy Aqua",
				Product.AMOUNT,
				"-4");


		// Should be: 2 - 2
		assertEquals(2, MONGO_DATASTORE.queryFromMongo(Product.BRAND, "Melitta").get(0).getAmount());

	}

	/**
	 * GoodCase
	 */
	@Test
	@DisplayName("MongoDB update String")
	void updateAnStringDataMongoDatabase() {

		MONGO_DATASTORE.saveIntoMongo(EASY_AQUA);
		MONGO_DATASTORE.updateIntoMongo(
				Product.PRODUCT_NAME,
				"1016-02 Easy Aqua",
				Product.BRAND,
				"Miele");

		// Should be: Miele - Miele
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

		// Should be: 1016-03 Easy Aqua - 1016-03 Easy Aqua
		assertEquals("1016-03 Easy Aqua", MONGO_DATASTORE.queryFromMongo(Product.PRODUCT_NAME, "1016-03 Easy Aqua").get(0).getProductName());

	}

	/**
	 * GoodCase
	 */
	@Test
	@DisplayName("OpenStreetMapService LonLat")
	void getLonLatOfOpenStreetMapService() {

		// Should be: 14.473361149999999 - 14.473361149999999 (Lon)
		assertEquals(14.473361149999999, NOMINATIM_SERVICE.getToLon());

		// Should be: 51.60227345 - 51.60227345 (Lat)
		assertEquals(51.60227345, NOMINATIM_SERVICE.getToLat());

		// assertEquals("", NOMINATIM_SERVICE.getLonLatResponse());

	}

	/**
	 * GoodCase
	 */
	@Test
	@DisplayName("OpenStreetMapService duration")
	void getDurationOfOpenStreetMapService() {

		// Should be: 14.549448333333334 - 14.549448333333334 (Minutes)
		assertEquals(14.549448333333334, OPEN_STREET_MAP_SERVICE.getDuration());

		// assertEquals("", OPEN_STREET_MAP_SERVICE.getDurationResponse());

	}

}