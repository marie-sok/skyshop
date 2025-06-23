package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final String productName;
    private final UUID id;

    public Product(UUID id, String productName) {
        this.id = id;
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Enter a short product's name. It can't be empty or contain only spaces");
        }
        this.productName = productName;
    }

    public Product(String productName) {
        this(UUID.randomUUID(), productName);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return productName;
    }

    public String getProductName() {
        return productName;
    }

    public abstract double getPrice();

    public abstract String getFormattedPrice();

    public abstract boolean isSpecial();

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return productName;
    }

    @JsonIgnore
    @Override
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public String toString() {
        return id + " " + productName + " ";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Product object = (Product) other;
        return Objects.equals(productName, object.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }
}
