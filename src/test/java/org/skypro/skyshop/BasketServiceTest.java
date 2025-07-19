package org.skypro.skyshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.exceptions.NoSuchProductException;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    private Product createTestProduct(String name, int price, UUID productId) {
        return new SimpleProduct(productId, name, price);
    }

    @Test
    void addProductBasketDoesNotExist_ShouldThrowException() {
        UUID nonExistentId = UUID.randomUUID();
        Mockito.when(storageService.getProductById(nonExistentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchProductException.class, () -> {
            basketService.addProductToBasket(nonExistentId);
        });

        Mockito.verify(storageService).getProductById(nonExistentId);
        Mockito.verifyNoInteractions(productBasket);
    }

    @Test
    void addProductBasket_WhenProductExists_ShouldAddToBasket() {
        UUID productId = UUID.randomUUID();
        Product product = createTestProduct("Earphones", 4500, productId);

        Mockito.when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        basketService.addProductToBasket(productId);

        Mockito.verify(storageService).getProductById(productId);
        Mockito.verify(productBasket).add(productId);
    }

    @Test
    void getUserBasket_WhenBasketIsEmpty_ShouldReturnEmptyBasket() {
        Mockito.when(productBasket.getBasket()).thenReturn(Collections.emptyMap());

        UserBasket result = basketService.getUserBasket();

        Assertions.assertTrue(result.items().isEmpty());
        Assertions.assertEquals(0.0, result.total());
        Mockito.verify(productBasket).getBasket();
        Mockito.verifyNoInteractions(storageService);
    }

    @Test
    void getUserBasket_WhenBasketHasProducts_ShouldReturnCorrectBasket() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        Product product1 = createTestProduct("Earphones", 4500, productId1);
        Product product2 = createTestProduct("Homepod", 15000, productId2);

        Map<UUID, Integer> basketContent = new HashMap<>();
        basketContent.put(productId1, 1);
        basketContent.put(productId2, 1);

        Mockito.when(productBasket.getBasket()).thenReturn(basketContent);
        Mockito.when(storageService.getProductById(productId1)).thenReturn(Optional.of(product1));
        Mockito.when(storageService.getProductById(productId2)).thenReturn(Optional.of(product2));

        UserBasket results = basketService.getUserBasket();

        Assertions.assertEquals(2, results.items().size());
        Assertions.assertEquals(19500, results.total());

        Assertions.assertTrue(results.items().contains(new BasketItem(product1, 1)));
        Assertions.assertTrue(results.items().contains(new BasketItem(product2, 1)));

        Mockito.verify(productBasket).getBasket();
        Mockito.verify(storageService).getProductById(productId1);
        Mockito.verify(storageService).getProductById(productId2);
    }

    @Test
    void getUserBasket_WhenProductInBasketNoLongerExistsShouldThrowException() {
        UUID removedProductId = UUID.randomUUID();
        Map<UUID, Integer> basketContent = Collections.singletonMap(removedProductId, 1);

        Mockito.when(productBasket.getBasket()).thenReturn(basketContent);
        Mockito.when(storageService.getProductById(removedProductId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchProductException.class, () -> {
            basketService.getUserBasket();
        });

        Mockito.verify(productBasket).getBasket();
        Mockito.verify(storageService).getProductById(removedProductId);
    }

    @Test
    void getUserBasket_ShouldCalculateTotalCorrectly() {
        UUID productId = UUID.randomUUID();
        Product product = createTestProduct("Pop-socket", 500, productId);
        int quantity = 1;

        Mockito.when(productBasket.getBasket()).thenReturn(Collections.singletonMap(productId, quantity));
        Mockito.when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        UserBasket result = basketService.getUserBasket();

        Assertions.assertEquals(500, result.total());
    }

    @Test
    void getUserBasketWithDiscountedProduct_ShouldCalculateTotalWithDiscount() {
        UUID productId = UUID.randomUUID();
        Product product = new DiscountedProduct(productId, "Earphones", 4500, 30);
        int quantity = 1;

        Mockito.when(productBasket.getBasket()).thenReturn(Collections.singletonMap(productId, quantity));
        Mockito.when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        UserBasket result = basketService.getUserBasket();
        Assertions.assertEquals(3150, result.total());
    }
}






