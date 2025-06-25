
package org.skypro.skyshop.model.article;

import org.skypro.skyshop.model.product.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements Searchable {
    private final String articleTitle;
    private final String contentOfArticle;
    private final UUID id;

    public Article(UUID id, String articleTitle, String contentOfArticle) {
        this.articleTitle = articleTitle;
        this.contentOfArticle = contentOfArticle;
        this.id = id;
    }

    public Article(String articleTitle, String contentOfArticle) {
        this(UUID.randomUUID(), articleTitle, contentOfArticle);
    }

    @Override
    public String toString() {
        return id + " " + articleTitle + " " + contentOfArticle;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Article object = (Article) other;
        return Objects.equals(contentOfArticle, object.contentOfArticle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentOfArticle);
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return toString();
    }

    @JsonIgnore
    @Override
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return articleTitle;
    }
}



