package com.huloteam.kbe.service;

import com.huloteam.kbe.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation (Impl) of the Interface ProductService.
 * @author Kevin Jagielski
 */
@Service
public class ProductServiceImpl implements ProductService {

    // private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final LocalDateTime now = LocalDateTime.now();
    private static final Map<Long, Product> productRepository = new HashMap<>();

    static {
        Product easyAqua = new Product();
        easyAqua.setId(1);
        easyAqua.setBrand("Melitta");
        easyAqua.setProductName("1016-02 Easy Aqua");
        easyAqua.setDescription("Schwarz");
        easyAqua.setProvider("Provider A");
        easyAqua.setProductTyp("Water boiler");
        easyAqua.setLocation("Storage 1");
        easyAqua.setAmount(6);
        easyAqua.setAvailableAmount(6);
        easyAqua.setProviderPrice(1794);
        easyAqua.setPriceWithoutTax(2097);
        easyAqua.setStoredSince(now);

        productRepository.put(easyAqua.getId(), easyAqua);
    }

    @Override
    public void createProduct(Product product) {
        productRepository.put(product.getId(), product);
    }

    @Override
    public void updateProduct(long id, Product product) {
        productRepository.remove(id);
        product.setId(id);
        productRepository.put(id, product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.remove(id);
    }

    @Override
    public Collection<Product> getProducts() {
        return productRepository.values();
    }
}
