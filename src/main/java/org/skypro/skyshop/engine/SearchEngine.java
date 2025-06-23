package org.skypro.skyshop.engine;

import org.skypro.skyshop.exceptions.BestResultNotFound;
import org.skypro.skyshop.model.search.Searchable;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


public final class SearchEngine {
    private final Set<Searchable> searchableItems = new HashSet<>();

    public void add(Searchable item) {
        searchableItems.add(item);
    }


    public Set<Searchable> search(String searchString) {

        return searchableItems.stream()
                .filter(item ->
                        item.getName().toLowerCase().contains(searchString.toLowerCase()))

                .collect(Collectors.toCollection(() -> new TreeSet<>(new org.skypro.skyshop.model.search.SearchResultComparator())));

    }



    public Searchable searchMostRelevant(String search) throws BestResultNotFound {
        Searchable bestMatch = null;
        int maxOccurrences = 0;


        for (Searchable searchable : searchableItems) {
            String searchTerm = searchable.getSearchTerm();
            int occurrences = countOccurrences(searchTerm, search);

            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
                bestMatch = searchable;
            }

            if (bestMatch == null) throw new BestResultNotFound(search);
        }
            return bestMatch;
        }



            private int countOccurrences (String text, String subString) {
                int count = 0;
                int index = 0;
                int subStringIndex;

                while ((subStringIndex = text.indexOf(subString, index)) != -1) {


                    count++;
                    index = subStringIndex + subString.length();

                }

                return count;
            }
        }







