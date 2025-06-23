package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> productsMap;
    private final Map<UUID, Article> articlesMap;

    public StorageService() {
        this.productsMap = new HashMap<>();
        this.articlesMap = new HashMap<>();
        createData();
    }

    public Collection<Product> getAllProducts() {
        return productsMap.values();
    }

    public Collection<Article> getAllArticles() {
        return articlesMap.values();
    }

    public Collection<Searchable> getAllSearchableResults() {
        List<Searchable> results = new ArrayList<>();
        results.addAll(productsMap.values());
        results.addAll(articlesMap.values());
        return results;
    }

    private void createData() {
        List<Product> testProducts = Arrays.asList(
                new SimpleProduct(UUID.randomUUID(), "PopSocket", 530),
                new FixPriceProduct(UUID.randomUUID(), "USB-C Cable"),
                new DiscountedProduct(UUID.randomUUID(), "Earphones", 4500, 30),
                new SimpleProduct(UUID.randomUUID(), "Adapter", 700),
                new SimpleProduct(UUID.randomUUID(), "Homepode", 15000),
                new SimpleProduct(UUID.randomUUID(), "Smart-watch", 3500),
                new SimpleProduct(UUID.randomUUID(), "Resident Evil: Village.PS5 Version", 3800),
                new DiscountedProduct(UUID.randomUUID(), "Disco Elysium. PS5 Version", 2500, 40)
        );

        for (Product product : testProducts) {
            productsMap.put(product.getId(), product);
        }

        List<Article> testArticles = Arrays.asList(
                new Article(UUID.randomUUID(), "PopSocket.", "This is universal phoneholder that allows you to comfortably hold your device in your hand and it's also a stylish accessory."),
                new Article(UUID.randomUUID(), "USB-C Cable.", "No one has ever tied you to the power supply as much as I have."),
                new Article(UUID.randomUUID(), "Earphones.", "The new noise cancellation function isolates you into space from the surrounding world."),
                new Article(UUID.randomUUID(), "Homepode", "That feeling when home is smarter than you.")
        );

        for (Article article : testArticles) {
            articlesMap.put(article.getId(), article);
        }
    }
}



