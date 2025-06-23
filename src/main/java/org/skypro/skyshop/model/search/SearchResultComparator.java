package org.skypro.skyshop.model.search;

import java.util.Comparator;

public class SearchResultComparator implements Comparator<Searchable> {

        @Override
        public int compare(Searchable o2, Searchable o1) {
            int lengthCompare = Integer.compare(o2.getName().length(), o1.getName().length());
            if (lengthCompare == 0) {
                return o2.getName().compareToIgnoreCase(o1.getName());
            }
            return lengthCompare;
        }
    }

