package org.skypro.skyshop;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.exceptions.NoSuchProductException;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.StorageService;

import java.util.*;
import java.util.stream.Collectors;

public class BasketServiceTest {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketServiceTest(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = Objects.requireNonNull(productBasket, "ProductBasket cannot be null");
        this.storageService = Objects.requireNonNull(storageService, "StorageService cannot be null");
    }

    public void addProduct(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        Product product = storageService.getProductById(productId).orElseThrow(() -> new NoSuchProductException("Product not found: " + productId));

        productBasket.addProduct(product.getId());
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> items = productBasket.getItems();

        List<BasketItem> basketItems = items.entrySet().stream().map(entry -> {
            UUID productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = storageService.getProductById(productId).orElseThrow(() -> new NoSuchProductException("Product not found: " + productId));

            return new BasketItem(product, quantity);
        }).collect(Collectors.toList());

        double totalPrice = basketItems.stream().mapToDouble(BasketItem::totalPrice).sum();

        return new UserBasket(basketItems, totalPrice);
    }

    public void clearBasket() {
        productBasket.clear();
    }
}