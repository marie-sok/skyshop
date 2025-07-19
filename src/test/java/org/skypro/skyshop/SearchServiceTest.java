package org.skypro.skyshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.SearchResults;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private Product createTestProduct(String name, UUID id, UUID productId) {
        return new SimpleProduct(productId,name, 500);
    }

    private Article createTestArticle(String title, UUID id) {
        return new Article(title, "Test");
    }

    @Test
    void search_WhenStorageIsEmpty_ReturnsEmptyList() {
        Mockito.when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        Collection<SearchResults> results = searchService.search("Tralalero Tralala");

        Assertions.assertTrue(results.isEmpty());
        Mockito.verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenNoMatchingItems_ReturnsEmptyList(UUID productId) {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Product product = createTestProduct("Homepod", id1, productId);
        Article article = createTestArticle("The Llama in the hat", id2);

        Mockito.when(storageService.getAllSearchables()).thenReturn(Arrays.asList(product, article));

        Collection<SearchResults> results = searchService.search("Non matching query");

        Assertions.assertTrue(results.isEmpty());
    }

    @Test
    void search_WhenMatchingProductExists_ReturnsProductResult(UUID productId) {
        UUID id = UUID.randomUUID();
        Product product = createTestProduct("Laptop", id, productId);

        Mockito.when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(product));

        Collection<SearchResults> results = searchService.search("Laptop");

        Assertions.assertEquals(1, results.size());
        SearchResults result = results.iterator().next();
        Assertions.assertEquals("Laptop", result.getName());
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals("PRODUCT", result.getContentType());
    }

    @Test
    void search_WhenMatchingArticleExists_ReturnsArticleResult() {
        UUID id = UUID.randomUUID();
        Article article = createTestArticle("Netflix Gift Cart", id);

        Mockito.when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(article));

        Collection<SearchResults> results = searchService.search("Netflix");

        Assertions.assertEquals(1, results.size());
        SearchResults result = results.iterator().next();
        Assertions.assertEquals("Netflix Gift Cart", result.getName());
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals("ARTICLE", result.getContentType());
    }

    @Test
    void search_WithEmptyQuery_ReturnsEmptyList() {
        Collection<SearchResults> results = searchService.search("");

        Assertions.assertTrue(results.isEmpty());
    }

    @Test
    void search_WhenMultipleMatchesExist_ReturnsAllResults(UUID productId) {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        Product product1 = createTestProduct("USB-C Cable", id1, productId);
        Article article1 = createTestArticle("Adapter", id2);
        Product product2 = createTestProduct("Homepod", id3, productId);

        Mockito.when(storageService.getAllSearchables()).thenReturn(Arrays.asList(product1, article1, product2));

        Collection<SearchResults> results = searchService.search("USB-C");

        Assertions.assertEquals(3, results.size());
        Assertions.assertTrue(results.stream().allMatch(r ->
                r.getName().toLowerCase().contains("usb-c")));
    }

    @Test
    void search_ResultContainsCorrectType() {
        UUID productId = UUID.randomUUID();
        UUID articleId = UUID.randomUUID();
        Product product = createTestProduct("Keyboard", productId, productId);
        Article article = createTestArticle("Adapter", articleId);

        Mockito.when(storageService.getAllSearchables()).thenReturn(Arrays.asList(product, article));

        Collection<SearchResults> results = searchService.search("Homepod");

        Assertions.assertEquals(2, results.size());
        Iterator<SearchResults> iterator = results.iterator();
        Assertions.assertEquals("PRODUCT", iterator.next().getContentType());
        Assertions.assertEquals("ARTICLE", iterator.next().getContentType());
    }
}



