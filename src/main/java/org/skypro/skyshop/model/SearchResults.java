package org.skypro.skyshop.model;

import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;

public final class SearchResults {

    private final String id;
    private final String name;
    private final String contentType;

    public SearchResults(String id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + contentType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        SearchResults object = (SearchResults) other;
        return Objects.equals(id, object.id) &&
                Objects.equals(name, object.name) &&
                Objects.equals(contentType, object.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, contentType);
    }

    public static SearchResults fromSearchable(Searchable searchable) {

        return new SearchResults(
                searchable.getId().toString(),
                searchable.getSearchTerm(),
                searchable.getContentType());
    }


}





