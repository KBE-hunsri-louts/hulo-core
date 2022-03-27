package com.huloteam.kbe.application;

import com.huloteam.kbe.model.Product;
import com.huloteam.kbe.odm.MongoDatastore;
import com.huloteam.kbe.service.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The core class in which all attributes will be gathered of one or all products.
 */
public class Application {
    private final MongoDatastore mongoDatastore = new MongoDatastore(false);
    private final NominatimService nominatimService = new NominatimServiceImpl();
    private final CalculatorService calculatorService = new CalculatorServiceImpl();
    private final StorageService storageService = new StorageServiceImpl();

    private Product product;

    /**
     * Provides one product.
     * @param genre is a String which contains a category or genre of a product.
     * @param genreInformation is a String with specific information to the category.
     * @param vatID is a String which contains tax information.
     * @return one product with all ten attributes.
     */
    public Product getSpecificProduct(String genre, String genreInformation, String vatID) {
        product = getMongoData(genre, genreInformation);
        getCalculatorData(vatID);
        // getStoredData(product.getProductName());

        return product;
    }

    /**
     * Get one of ten product attributes of the external api Nominatim.
     */
    public void getNominatimData(String street, String city, String zip) {
        nominatimService.startApi(street, city, zip);

        if (product != null) {
            double[] lonLatCoordinates = new double[2];
            lonLatCoordinates[0] = nominatimService.getToLon();
            lonLatCoordinates[1] = nominatimService.getToLat();

            product.setSpecificLocation(lonLatCoordinates);
        }
    }

    /**
     * Get five of ten product attributes stored in mongo database.
     * @param genre is a String which contains a category or genre of a product.
     * @param genreInformation is a String with specific information to the category.
     * @return a product with five attributes.
     */
    private Product getMongoData(String genre, String genreInformation) {
        List<Product> productList = mongoDatastore.queryFromMongo(genre, genreInformation);

        if (productList.size() != 0) {
            if (productList.size() == 1) {
                return productList.get(0);
            }
        }

        return null;
    }

    /**
     * Get one of ten product attributes from calculator component.
     * @param vatID is a String which says the component which tax he should use
     */
    private void getCalculatorData(String vatID) {
        double calculatedPrice = calculatorService.getPriceWithTax(
                vatID,
                String.valueOf(product.getPriceWithoutTax()));

        product.setPriceWithTax((int) (calculatedPrice * 100));
    }

    /**
     * Get three of ten product attributes stored in storage component.
     * @param productName is a String and needed to find the right product information
     */
    /*
    private void getStoredData(String productName) {
        List<String> productInformation = storageService.getStorageProductInformation(productName);

        if (productInformation != null) {
            product.setProvider(productInformation.get(0));
            product.setProviderPrice(Integer.parseInt(productInformation.get(1)));
            product.setStoredSince(LocalDateTime.parse(productInformation.get(2)));
        }
    }

     */
}
