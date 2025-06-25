package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final double FIX_PRICE = 100;

    public FixPriceProduct(UUID id, String name) {
        super(name);
    }


    @Override
    public double getPrice() {
        return FIX_PRICE;
    }

    @Override
    public boolean getFormattedPrice() {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return new StringBuilder("FixPrice!")
                .append(getName())
                .append(": ")
                .append(String.format("%.2f ₽", getPrice()))
                .toString();
    }
}

