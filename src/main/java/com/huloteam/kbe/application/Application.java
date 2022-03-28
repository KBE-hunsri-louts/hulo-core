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
    private final OpenStreetMapService openStreetMapService = new OpenStreetMapServiceImpl();
    private final StorageService storageService = new StorageServiceImpl();

    private Product product;

    /**
     * Provides one product.
     * @param genre is a String which contains a category or genre of a product.
     * @param genreInformation is a String with specific information to the category.
     * @return one product with all ten attributes.
     */
    public Product getSpecificProduct(String genre, String genreInformation) {
        product = getMongoData(genre, genreInformation);
        // getStoredData(product.getProductName());

        return product;
    }

    /**
     * Get latitude and longitude of a location.
     * @param street is a string which contains a street name
     * @param houseNumber is a string of a house number
     * @param city is a string which contains the name of a city
     * @param zip is a string which only contains numbers
     * @return a double array with the longitude and latitude of a location
     */
    public double[] getNominatimData(String street, String houseNumber, String city, String zip) {
        nominatimService.startApi(street, houseNumber, city, zip);

        double[] lonLatCoordinates = new double[2];
        lonLatCoordinates[0] = nominatimService.getToLon();
        lonLatCoordinates[1] = nominatimService.getToLat();

        return lonLatCoordinates;
    }

    /**
     * Get duration of a location.
     * @param street is a string containing letters
     * @param houseNumber is a string containing letter
     * @param city is a string containing letters
     * @param zip is a string containing numbers
     * @return a double number
     */
    public double getORSMDurationViaLocationInformation(String street, String houseNumber, String city, String zip) {
        openStreetMapService.startApi(street, houseNumber, city, zip);
        return openStreetMapService.getDuration();
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
