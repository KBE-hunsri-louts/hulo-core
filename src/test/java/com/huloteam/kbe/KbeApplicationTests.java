package com.huloteam.kbe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.huloteam.kbe.model.Product;
import com.huloteam.kbe.odm.MongoDatastore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class KbeApplicationTests {

	private final MongoDatastore mongoDatastore = new MongoDatastore();
	private static final LocalDateTime NOW = LocalDateTime.now();
	private static final Product EASYAQUA = new Product();

	static {
		EASYAQUA.setId(1);
		EASYAQUA.setBrand("Melitta");
		EASYAQUA.setProductName("1016-02 Easy Aqua");
		EASYAQUA.setDescription("Schwarz");
		EASYAQUA.setProvider("Provider A");
		EASYAQUA.setProductTyp("Water boiler");
		EASYAQUA.setLocation("Storage 1");
		EASYAQUA.setAmount(6);
		EASYAQUA.setAvailableAmount(6);
		EASYAQUA.setProviderPrice(1794);
		EASYAQUA.setPriceWithoutTax(2097);
		EASYAQUA.setStoredSince(NOW);
	}

	@Test
	void putIntoMongoDatabase() {
		mongoDatastore.saveIntoMongo(EASYAQUA);

		List<Product> productList = mongoDatastore.queryFromMongo("productName", "1016-02 Easy Aqua");

		// Should be: Melitta -> Melitta
		assertEquals(EASYAQUA.getBrand(), productList.get(0).getBrand());
		// Should be: 1016-02 Easy Aqua -> 1016-02 Easy Aqua
		assertEquals(EASYAQUA.getProductName(), productList.get(0).getProductName());
	}

	@Test
	void deleteDataMongoDatabase() {
		mongoDatastore.saveIntoMongo(EASYAQUA);
		mongoDatastore.deleteProductInMongo("productName", "1016-02 Easy Aqua");

		List<Product> productList1 = mongoDatastore.queryFromMongo("productName", EASYAQUA.getBrand());
		List<Product> productList2 = new LinkedList<>();

		// Should be: [] -> []
		assertEquals(productList1, productList2);
	}

	@Test
	void updateAnIntegerDataMongoDatabase() {
		mongoDatastore.saveIntoMongo(EASYAQUA);
		mongoDatastore.updateIntoMongo(
				"productName",
				"1016-02 Easy Aqua",
				"amount",
				"-4");

		// Should be: 2 -> 2
		assertEquals(2, mongoDatastore.queryFromMongo("brand", "Melitta").get(0).getAmount());
	}

	@Test
	void updateAnStringDataMongoDatabase() {
		mongoDatastore.saveIntoMongo(EASYAQUA);
		mongoDatastore.updateIntoMongo(
				"productName",
				"1016-02 Easy Aqua",
				"brand",
				"Miele");

		// Should be: Miele -> Miele
		assertEquals("Miele", mongoDatastore.queryFromMongo("productName", "1016-02 Easy Aqua").get(0).getBrand());
	}

}