package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.product.Product;

import java.util.*;
import java.util.stream.Stream;

public class ProductBasket {
    private final Map<String, LinkedList<Product>> productBasket = new HashMap<>();



    public void addProduct(Product product) {
        productBasket.computeIfAbsent(product.getName(), k -> new LinkedList<>()).add(product);

    }


    public double calculateSumOfBasket() {
        double sum = productBasket.values().stream().flatMap(Collection::stream)
                .mapToDouble(x -> x.getPrice())
                .sum();
        return sum;


    }


    private long calculateSpecialProducts() {
        return productBasket.values().stream().flatMap(Collection::stream)
                .filter(x -> x.isSpecial())
                .count();
    }

    public void printProductBasket() {
        double sum = calculateSumOfBasket();
        Stream<Product> basket = productBasket.values().stream().flatMap(Collection::stream);
        if (sum == 0) {
            System.out.println("The basket is empty");

        } else {
            long specialGoods = calculateSpecialProducts();
            basket.forEach(System.out::println);
            System.out.println("Total: " + sum);
            System.out.println("Special Goods: " + specialGoods);
        }
    }


    public boolean searchProduct(String name) {
        return productBasket.values().stream().flatMap(Collection::stream)
                .anyMatch(p -> p.getName().equalsIgnoreCase(name));
    }

    public void clear() {
        productBasket.clear();


    }

    public List<Product> removeThisProduct(String name) {
        List<Product> removedProducts = new LinkedList<>();
        if (productBasket.containsKey(name)) {
            removedProducts.addAll(productBasket.remove(name));


        }
        if (removedProducts.isEmpty()) {
            System.out.println("List is Empty");
        }
        return removedProducts;
    }
}




