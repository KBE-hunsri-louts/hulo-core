package com.huloteam.kbe.odm;

import com.huloteam.kbe.model.Product;
import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

/**
 * ODM which communicates with the mongo database
 * @author Kevin Jagielski
 */
public class MongoDatastore {
    private static Datastore datastore;

    /**
     * Constructor.
     * @param isTestDatabase true - create test MongoDB, false - create "real" MongoDB
     */
    public MongoDatastore(boolean isTestDatabase) {
        if (isTestDatabase) {
            Morphia morphia = new Morphia();
            morphia.mapPackage("com.huloteam.kbe.model.product.class");

            datastore = morphia.createDatastore(new MongoClient(), "productStorageTest");
            datastore.ensureIndexes();
        } else {
            Morphia morphia = new Morphia();
            morphia.mapPackage("com.huloteam.kbe.model.product.class");

            datastore = morphia.createDatastore(new MongoClient(), "productStorage");
            datastore.ensureIndexes();
        }
    }

    /**
     * Saves a product into a mongo database.
     * @param product which contains data of a real product.
     */
    public void saveIntoMongo(Product product) {
        datastore.save(product);
    }

    /**
     * Gets all products which got the information we are looking for.
     * For examples please look into KbeApplicationTests.
     * @param category is a String which contains a category or genre of a product.
     * @param productCategoryToLookFor is a String with specific information to the category.
     * @return a list of products.
     */
    public List<Product> queryFromMongo(String category, String productCategoryToLookFor) {
        return datastore.createQuery(Product.class)
                .field(category)
                .contains(productCategoryToLookFor)
                .find()
                .toList();
    }

    /**
     * Gets all products of mongo database.
     * @return a list of all products.
     */
    public List<Product> queryAllFromMongo() {
        return datastore.createQuery(Product.class).find().toList();
    }

    /**
     * Updates a product in mongo database.
     * For examples please look into KbeApplicationTests.
     * @param category is a String which contains a category or genre of a product.
     * @param productCategoryToUpdate is a String with specific information to the category.
     * @param genreToBeUpdated is a String which contains a category or genre of a product.
     * @param updateInformation is a String with specific information to the category which will be changed.
     */
    public void updateIntoMongo(String category, String productCategoryToUpdate, String genreToBeUpdated, String updateInformation) {
        Query<Product> query = datastore.createQuery(Product.class)
                .field(category)
                .contains(productCategoryToUpdate);

        UpdateOperations<Product> updateOperations;

        // https://www.baeldung.com/java-check-string-number
        if (NumberUtils.isCreatable(updateInformation)) {
            updateOperations = datastore.createUpdateOperations(Product.class).inc(genreToBeUpdated, Integer.valueOf(updateInformation));
        } else {
            updateOperations = datastore.createUpdateOperations(Product.class).set(genreToBeUpdated, updateInformation);
        }

        if (updateOperations != null) {
            datastore.update(query, updateOperations);
        }
    }

    /**
     * Deletes a product of mongo database.
     * For examples please look into KbeApplicationTests.
     * @param category is a String which contains a category or genre of a product.
     * @param productCategoryToDelete is a String with specific information to the category.
     */
    public void deleteProductInMongo(String category, String productCategoryToDelete) {
        Query<Product> query = datastore.createQuery(Product.class)
                .field(category)
                .contains(productCategoryToDelete);

        datastore.delete(query);
    }
}
