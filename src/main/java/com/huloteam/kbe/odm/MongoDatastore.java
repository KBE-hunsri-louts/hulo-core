package com.huloteam.kbe.odm;

import com.huloteam.kbe.model.Product;
import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;

import java.util.List;

/**
 * ODM which communicates with the mongo database
 * @author Kevin Jagielski
 */
public class MongoDatastore {

    private static Datastore datastore;

    public MongoDatastore(boolean testDatabase) {
        if (testDatabase) {
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

    public void saveIntoMongo(Product product) {
        datastore.save(product);
    }

    public List<Product> queryFromMongo(String category, String productCategoryToLookFor) {
        return datastore.createQuery(Product.class)
                .field(category)
                .contains(productCategoryToLookFor)
                .find()
                .toList();
    }

    public void updateIntoMongo(String category, String productCategoryToUpdate, String genreToBeUpdated, String updateInformation) {
        Query<Product> query = datastore.createQuery(Product.class)
                .field(category)
                .contains(productCategoryToUpdate);

        UpdateOperations<Product> updateOperations;

        if (updateInformation.matches(".*\\d.*")) {
            updateOperations = datastore.createUpdateOperations(Product.class).inc(genreToBeUpdated, Integer.valueOf(updateInformation));
        } else {
            updateOperations = datastore.createUpdateOperations(Product.class).set(genreToBeUpdated, updateInformation);
        }

        if (updateOperations != null) {
            datastore.update(query, updateOperations);
        }
    }

    public void deleteProductInMongo(String category, String productCategoryToDelete) {
        Query<Product> query = datastore.createQuery(Product.class)
                .field(category)
                .contains(productCategoryToDelete);

        datastore.delete(query);
    }

}
