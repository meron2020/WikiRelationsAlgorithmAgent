package org.wikiRelationsAlgorithm.core.rankingMechanisms;

import java.util.*;

public class MutualLinks {
    public static Integer findMutualLinks(ArrayList<String> originalLinks, ArrayList<String> queryLinks) {
        originalLinks.retainAll(queryLinks);
        return originalLinks.size();
    }

    public static Hashtable<String, Integer> findAllMutualLinks
            (ArrayList<String> originalLinks, HashMap<String, ArrayList<String>> queryLinksHash) {
        Hashtable<String, Integer> mutualLinks = new Hashtable<>();

        for (String queryLink: queryLinksHash.keySet()) {
            int mutualLinksNum = findMutualLinks(originalLinks, queryLinksHash.get(queryLink));
            mutualLinks.put(queryLink, mutualLinksNum);
        }
        return mutualLinks;
    }

    public static ArrayList<Map.Entry<String, Integer>> sort(Hashtable<String, Integer> queryIndexes) {
        ArrayList<Map.Entry<String, Integer>> listToSort = new ArrayList<>(queryIndexes.entrySet());
        listToSort.sort(Map.Entry.comparingByValue());
        return listToSort;
    }

}
