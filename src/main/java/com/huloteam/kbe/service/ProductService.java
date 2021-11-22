package com.huloteam.kbe.service;

import com.huloteam.kbe.model.Product;

import java.util.Collection;

/**
 * Is an interface to create Services for the Product Model.
 * @author Kevin Jagielski
 */
public interface ProductService {

    void createProduct(Product product);
    void updateProduct(long id, Product product);
    void deleteProduct(long id);
    Collection<Product> getProducts();
}
