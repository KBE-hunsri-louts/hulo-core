package service;

import model.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductServiceImpl implements ProductService {
    // private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final LocalDateTime now = LocalDateTime.now();
    private static final Map<Long, Product> productRepo = new HashMap<>();

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
        easyAqua.setPriceWithTax(0); // Needs to be changed in an object
        easyAqua.setStoredSince(now);

        productRepo.put(easyAqua.getId(), easyAqua);
    }

    @Override
    public void createProduct(Product product) {
        productRepo.put(product.getId(), product);
    }

    @Override
    public void updateProduct(long id, Product product) {
        productRepo.remove(id);
        product.setId(id);
        productRepo.put(id, product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepo.remove(id);
    }

    @Override
    public Collection<Product> getProducts() {
        return productRepo.values();
    }
}
