package com.huloteam.kbe.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model class which contains information about a real product
 * @author Kevin Jagielski
 */
@Entity("Product")
public class Product {
    // Mongo
    @Id
    private long id;
    private String productName;
    private String description;
    private String brand;
    private String productTyp;
    private String location;
    private int amount;
    private int availableAmount;
    private int priceWithoutTax;

    // Storage - PostgreSQL
    private String provider;
    private int providerPrice;
    private LocalDateTime storedSince;

    public static final String ID = "id";
    public static final String PRODUCT_NAME = "productName";
    public static final String DESCRIPTION = "description";
    public static final String BRAND = "brand";
    public static final String PROVIDER = "provider";
    public static final String PRODUCT_TYP = "productTyp";
    public static final String LOCATION = "location";
    public static final String AMOUNT = "amount";
    public static final String AVAILABLE_AMOUNT = "availableAmount";
    public static final String PRICE_WITHOUT_TAX = "priceWithoutTax";
    public static final String PROVIDER_PRICE = "providerPrice";
    public static final String STORED_SINCE = "storedSince";


    public long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getProvider() {
        return provider;
    }

    public String getProductTyp() {
        return productTyp;
    }

    public String getLocation() {
        return location;
    }

    public int getAmount() {
        return amount;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public int getPriceWithoutTax() {
        return priceWithoutTax;
    }

    public int getProviderPrice() {
        return providerPrice;
    }

    public LocalDateTime getStoredSince() {
        return storedSince;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setProductTyp(String productTyp) {
        this.productTyp = productTyp;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public void setPriceWithoutTax(int priceWithoutTax) {
        this.priceWithoutTax = priceWithoutTax;
    }

    public void setProviderPrice(int providerPrice) {
        this.providerPrice = providerPrice;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setStoredSince(LocalDateTime storedSince) {
        this.storedSince = storedSince;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && amount == product.amount && availableAmount == product.availableAmount && priceWithoutTax == product.priceWithoutTax && providerPrice == product.providerPrice && Objects.equals(productName, product.productName) && Objects.equals(description, product.description) && Objects.equals(brand, product.brand) && Objects.equals(productTyp, product.productTyp) && Objects.equals(location, product.location) && Objects.equals(provider, product.provider) && Objects.equals(storedSince, product.storedSince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, description, brand, productTyp, location, amount, availableAmount, priceWithoutTax, provider, providerPrice, storedSince);
    }

    @Override
    public String toString() {
        return "Product{" +
                ID + "=" + id +
                ", " + BRAND + "='" + brand + '\'' +
                ", " + PRODUCT_NAME + "='" + productName + '\'' +
                ", " + PRICE_WITHOUT_TAX + "=" + priceWithoutTax +
                ", " + AMOUNT + "=" + amount +
                ", " + AVAILABLE_AMOUNT + "=" + availableAmount +
                ", " + PRODUCT_TYP + "='" + productTyp + '\'' +
                ", " + DESCRIPTION + "='" + description + '\'' +
                ", " + PROVIDER + "='" + provider + '\'' +
                ", " + PROVIDER_PRICE + "=" + providerPrice +
                ", " + LOCATION + "='" + location + '\'' +
                ", " + STORED_SINCE + "=" + storedSince +
                '}';
    }
}
