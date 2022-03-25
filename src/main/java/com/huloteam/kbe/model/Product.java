package com.huloteam.kbe.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    // Calculator
    private int priceWithTax;

    // Storage - PostgreSQL
    private String provider;
    private int providerPrice;
    private LocalDateTime storedSince;

    // External API
    private double[] specificLocation = new double[2];

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
    public static final String PRICE_WITH_TAX = "priceWithTax";
    public static final String PROVIDER_PRICE = "providerPrice";
    public static final String STORED_SINCE = "storedSince";
    public static final String SPECIFIC_LOCATION = "specificLocation";


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

    public int getPriceWithTax() {
        return priceWithTax;
    }

    public int getProviderPrice() {
        return providerPrice;
    }

    public LocalDateTime getStoredSince() {
        return storedSince;
    }

    public double[] getSpecificLocation() {
        return specificLocation;
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

    public void setPriceWithTax(int priceWithTax) {
        this.priceWithTax = priceWithTax;
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

    public void setSpecificLocation(double[] specificLocation) {
        this.specificLocation = specificLocation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && amount == product.amount && availableAmount == product.availableAmount && priceWithoutTax == product.priceWithoutTax && priceWithTax == product.priceWithTax && providerPrice == product.providerPrice && Objects.equals(productName, product.productName) && Objects.equals(description, product.description) && Objects.equals(brand, product.brand) && Objects.equals(provider, product.provider) && Objects.equals(productTyp, product.productTyp) && Objects.equals(location, product.location) && Objects.equals(storedSince, product.storedSince) && Arrays.equals(specificLocation, product.specificLocation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, productName, description, brand, provider, productTyp, location, amount, availableAmount, priceWithoutTax, priceWithTax, providerPrice, storedSince);
        result = 31 * result + Arrays.hashCode(specificLocation);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                ID + "=" + id +
                ", " + BRAND + "='" + brand + '\'' +
                ", " + PRODUCT_NAME + "='" + productName + '\'' +
                ", " + PRICE_WITHOUT_TAX + "=" + priceWithoutTax +
                ", " + PRICE_WITH_TAX + "=" + priceWithTax +
                ", " + AMOUNT + "=" + amount +
                ", " + AVAILABLE_AMOUNT + "=" + availableAmount +
                ", " + PRODUCT_TYP + "='" + productTyp + '\'' +
                ", " + DESCRIPTION + "='" + description + '\'' +
                ", " + PROVIDER + "='" + provider + '\'' +
                ", " + PROVIDER_PRICE + "=" + providerPrice +
                ", " + LOCATION + "='" + location + '\'' +
                ", " + SPECIFIC_LOCATION + Arrays.toString(specificLocation) +
                ", " + STORED_SINCE + "=" + storedSince +
                '}';
    }

}
