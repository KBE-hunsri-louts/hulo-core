package com.huloteam.kbe.application;

import com.huloteam.kbe.model.Product;
import com.huloteam.kbe.odm.MongoDatastore;
import com.huloteam.kbe.service.CalculatorService;
import com.huloteam.kbe.service.CalculatorServiceImpl;
import com.huloteam.kbe.service.NominatimService;
import com.huloteam.kbe.service.NominatimServiceImpl;

import java.util.List;

/**
 * The core class in which all attributes will be gathered of one or all products.
 */
public class Application {

    private final MongoDatastore mongoDatastore = new MongoDatastore(false);
    private final NominatimService nominatimService = new NominatimServiceImpl();
    private final CalculatorService calculatorService = new CalculatorServiceImpl();

    private Product product;

    /**
     * TODO public?
     * Provides one product.
     * @param genre is a String which contains a category or genre of a product.
     * @param genreInformation is a String with specific information to the category.
     * @param street is name of a street.
     * @param city is name of a city.
     * @param zip is an identity number for a city or state.
     * @return one product with all ten attributes.
     */
    public Product getSpecificProduct(String genre, String genreInformation,
                                      String street, String city, String zip,
                                      String vatID) {

        product = getMongoData(genre, genreInformation);
        getNominatimData(street, city, zip);
        getCalculatorData(vatID);
        // getStoredData();

        return product;

    }

    /**
     * TODO public?
     * Will provide all products in one list.
     * @return a list with all products.
     */
    public List<Product> getAllProducts() {

        List<Product> returnList = mongoDatastore.queryAllFromMongo();

        for (Product product : returnList) {

            // TODO wont work!
            // getNominatimData();

        }

        return returnList;
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
     * Get one of ten product attributes of the external api Nominatim.
     */
    private void getNominatimData(String street, String city, String zip) {

        nominatimService.startApi(street, city, zip);

        if (product != null) {

            double[] lonLatCoordinates = new double[2];
            lonLatCoordinates[0] = nominatimService.getToLon();
            lonLatCoordinates[1] = nominatimService.getToLat();

            product.setSpecificLocation(lonLatCoordinates);

        }

    }

    private void getCalculatorData(String vatID) {

        double calculatedPrice = calculatorService.getPriceWithTax(
                vatID,
                String.valueOf(product.getPriceWithoutTax()));

        product.setPriceWithTax((int) (calculatedPrice * 100));

    }

    private void getStoredData() {



    }

}
