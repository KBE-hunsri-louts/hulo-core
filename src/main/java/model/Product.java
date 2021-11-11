package model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Model class which contains information about a real product
 * @author Kevin Jagielski
 */
public class Product {

    private long id; // change to GUID
    private String productName;
    private String description;
    private String brand;
    private String provider;
    private String productTyp;
    private String location;
    private int amount;
    private int availableAmount;
    private int priceWithoutTax;
    private int priceWithTax;
    private int providerPrice;
    private LocalDateTime storedSince;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && amount == product.amount && availableAmount == product.availableAmount && Double.compare(product.priceWithoutTax, priceWithoutTax) == 0 && Double.compare(product.priceWithTax, priceWithTax) == 0 && Double.compare(product.providerPrice, providerPrice) == 0 && Objects.equals(productName, product.productName) && Objects.equals(description, product.description) && Objects.equals(brand, product.brand) && Objects.equals(provider, product.provider) && Objects.equals(productTyp, product.productTyp) && Objects.equals(location, product.location) && Objects.equals(storedSince, product.storedSince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, description, brand, provider, productTyp, location, amount, availableAmount, storedSince, priceWithoutTax, priceWithTax, providerPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", productName='" + productName + '\'' +
                ", priceWithTax=" + priceWithTax +
                ", priceWithoutTax=" + priceWithoutTax +
                ", amount=" + amount +
                ", availableAmount=" + availableAmount +
                ", productTyp='" + productTyp + '\'' +
                ", description='" + description + '\'' +
                ", provider='" + provider + '\'' +
                ", providerPrice=" + providerPrice +
                ", location='" + location + '\'' +
                ", storedSince=" + storedSince +
                '}';
    }
}
