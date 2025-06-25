import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.engine.SearchEngine;
import org.skypro.skyshop.exceptions.BestResultNotFound;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;


public class App {
    public static void main(String[] args) throws IllegalArgumentException {

        Product product1 = new SimpleProduct(UUID.randomUUID(),"PopSocket", 530);
        Product product2 = new FixPriceProduct(UUID.randomUUID(), "USB-C Cable");
        Product product3 = new DiscountedProduct(UUID.randomUUID(), "Earphones", 4500, 30);
        Product product4 = new SimpleProduct(UUID.randomUUID(), "Adapter", 700);
        Product product5 = new SimpleProduct(UUID.randomUUID(), "Homepod", 15000);
        Product product6 = new SimpleProduct(UUID.randomUUID(), "Smart-watch", 3500);
        Product product7 = new SimpleProduct(UUID.randomUUID(), "Resident Evil: Village.PS5 Version", 3800);
        Product product8 = new DiscountedProduct(UUID.randomUUID(), "Disco Elysium. PS5 Version", 2500, 40);

        Article article1 = new Article("PopSocket.", "This is universal phoneholder that allows you to comfortably hold your device in your hand and it's also a stylish accessory.") {
            @Override
            public String getName() {
                return super.getName();
            }
        };
        Article article2 = new Article("USB-C Cable.", "No one has ever tied you to the power supply as much as I have.") {
            @Override
            public String getName() {
                return super.getName();
            }
        };

        Article article3 = new Article("Earphones.", "The new noise cancellation function isolates you into space from the surrounding world.") {
            @Override
            public String getName() {
                return "";
            }
        };

        Article article4 = new Article("Adapter", "I'll charge your device at the speed of light") {
            @Override
            public String getName() {
                return "";
            }
        };

        ProductBasket firstProductBasket = new ProductBasket();

        firstProductBasket.addProduct(product1);
        firstProductBasket.addProduct(product2);
        firstProductBasket.addProduct(product3);
        firstProductBasket.addProduct(product4);

        ProductBasket secondProductBasket = new ProductBasket();
        secondProductBasket.addProduct(product5);
        secondProductBasket.addProduct(product6);
        secondProductBasket.addProduct(product7);
        secondProductBasket.addProduct(product8);

        firstProductBasket.printProductBasket();
        System.out.println();


        secondProductBasket.printProductBasket();

        System.out.println();

        System.out.println(firstProductBasket.searchProduct("PopSocket"));
        System.out.println(firstProductBasket.searchProduct("USB-C Cable"));
        System.out.println(secondProductBasket.searchProduct("Earphones"));
        System.out.println();

        System.out.println(firstProductBasket.removeThisProduct("Adapter"));
        System.out.println();


        firstProductBasket.printProductBasket();
        System.out.println();

        System.out.println(secondProductBasket.removeThisProduct("Tralalero Tralala"));


        firstProductBasket.clear();
        firstProductBasket.printProductBasket();
        System.out.println();
        System.out.println(firstProductBasket.searchProduct("Earphones"));
        SearchEngine searchEngine = new SearchEngine();


        searchEngine.add(product1);
        searchEngine.add(product2);
        searchEngine.add(product3);
        searchEngine.add(product4);
        searchEngine.add(product5);
        searchEngine.add(product6);
        searchEngine.add(product7);
        searchEngine.add(product8);
        searchEngine.add(article1);
        searchEngine.add(article2);
        searchEngine.add(article3);
        searchEngine.add(article4);
        System.out.println();


        System.out.println();
        System.out.println(searchEngine.search("Homepode"));
        System.out.println(searchEngine.search("Resident Evil:Village. PS5 Version"));
        System.out.println(searchEngine.search("Disco Elysium. PS5 Version"));


        try {
            Searchable bestMatch = searchEngine.searchMostRelevant("PopSocket");
            System.out.println("Best match object: " + bestMatch.getSearchTerm());
        } catch (BestResultNotFound e) {
            System.err.println(e);
        }

        try {
            product5 = new SimpleProduct(UUID.randomUUID(),"HomePod", 15000);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }

        try {
            product6 = new SimpleProduct(UUID.randomUUID(),"Smart-Watch", 3500);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
        try {
            product7 = new SimpleProduct(UUID.randomUUID(),"Resident Evil: Village.PS5 Version", 3800);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            product8 = new DiscountedProduct(UUID.randomUUID(),"Disco Elysium.PS5 Version", 2800, 40);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }


}



