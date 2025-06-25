package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.SearchResults;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;


@RestController
@RequestMapping("/shop")
public class ShopController {

    private final StorageService storageService;
    private final SearchService searchService;

    public ShopController(StorageService storageService, SearchService searchService) {
        this.storageService = storageService;
        this.searchService = searchService;
    }
    @GetMapping("/products")
    public Collection<Product> getProducts() {
        return storageService.getAllProducts();
    }
    @GetMapping("/articles")
    public Collection<Article> getArticles() {
            return storageService.getAllArticles();
        }

    @GetMapping("/search")
    public Collection<SearchResults> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }
}


