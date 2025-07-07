package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
import java.util.stream.Stream;


@Component
@SessionScope
public class ProductBasket {
    private final Map<UUID, Integer> productBasket = new HashMap<>();

    public void add(UUID id) {
        productBasket.merge(id, 1, Integer::sum);
    }

        public Map<UUID, Integer> getBasket() {
            return Collections.unmodifiableMap(productBasket);

        }
    }







