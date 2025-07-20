package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.SearchResults;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;
import org.skypro.skyshop.model.service.BasketService;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.UUID;


@RestController
@RequestMapping("/shop")
public class ShopController {

    private final StorageService storageService;
    private final SearchService searchService;
    private final org.skypro.skyshop.model.service.BasketService basketService;

    public ShopController(StorageService storageService, SearchService searchService, BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
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

    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addProductToBasket(id);
        System.out.println("Request to /basket/" + id + "is received");
        return "Product successfully added";
    }

    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        System.out.println("Request to /basket/ is received");
        return basketService.getUserBasket();
    }
}


