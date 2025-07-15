package org.skypro.skyshop;

import org.skypro.skyshop.model.SearchResults;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.StorageService;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class SearchServiceTest {
    private final StorageService storageService;

    public SearchServiceTest(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResults> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be null or empty");
        }

        Collection<Searchable> allSearchables = storageService.getAllSearchableResults();
        if (allSearchables == null || allSearchables.isEmpty()) {
            return Collections.emptyList();
        }

        return allSearchables.stream()
                .filter(searchable -> matchesQuery(searchable, query))
                .map(this::convertToSearchResult)
                .collect(Collectors.toList());
    }

    private boolean matchesQuery(Searchable searchable, String query) {
        return searchable.getSearchTerm().toLowerCase().contains(query.toLowerCase());
    }

    private SearchResults convertToSearchResult(Searchable searchable) {
        return new SearchResults(
                searchable.getId().toString(),
                searchable.getName(),
                searchable.getTypeOfContent()
        );
    }
}