package org.skypro.skyshop.service;

import org.skypro.skyshop.model.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<Searchable> getAllItemsForSearch() {
        return storageService.getAllSearchableResults();
    }

    public Collection<SearchResult> search(String query) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Type name of product or category");
        }
        return getAllItemsForSearch().stream()
                .filter(searchable -> isValidSearchable(searchable) && containsSearchTerm(query, searchable))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());

    }

    public boolean isValidSearchable(Searchable searchable) {
        return searchable != null && searchable.getSearchTerm() != null;
    }

    public boolean containsSearchTerm(String name, Searchable searchable) {
        return searchable.getSearchTerm().toLowerCase().contains(name.toLowerCase());
    }
}
