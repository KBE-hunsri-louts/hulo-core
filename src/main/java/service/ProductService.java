package service;

import model.Product;

import java.util.Collection;

public interface ProductService {

    void createProduct(Product product);
    void updateProduct(long id, Product product);
    void deleteProduct(long id);
    Collection<Product> getProducts();
}
