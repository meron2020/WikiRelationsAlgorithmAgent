package org.wikiRelationsAlgorithm.core.rankingMechanisms;

import java.util.*;

public class OrderOfAppearance {
    public static Hashtable<String, Integer> findQueryIndexes(String originalText, ArrayList<String> queries) {
        Hashtable<String, Integer> valueIndexes = new Hashtable<>();
        for (String query: queries) {
            valueIndexes.put(query, originalText.indexOf(query));
        }
        return valueIndexes;
    }

    public static ArrayList<Map.Entry<String, Integer>> sort(Hashtable<String, Integer> queryIndexes) {
        ArrayList<Map.Entry<String, Integer>> listToSort = new ArrayList<Map.Entry<String, Integer>>(queryIndexes.entrySet());
        listToSort.sort(Map.Entry.comparingByValue());
        return listToSort;
    }

    public static ArrayList<Map.Entry<String, Integer>> getOrderOfAppearance(String originalText, ArrayList<String> queries) {
        Hashtable<String, Integer> queryIndexes = findQueryIndexes(originalText, queries);
        return sort(queryIndexes);
    }
}
