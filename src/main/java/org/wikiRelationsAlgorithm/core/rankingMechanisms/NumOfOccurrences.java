package org.wikiRelationsAlgorithm.core.rankingMechanisms;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Hashtable;

public class NumOfOccurrences {
    public static int numOfOccurrences(String originalText, String query) {
        return StringUtils.countMatches(originalText, query);
    }

    public static Hashtable<String, Integer> checkAllQueries(String originalText, ArrayList<String> queries) {
        Hashtable<String, Integer> numOfOccurrencesPerQuery = new Hashtable<>();
        for (String query : queries) {
            numOfOccurrencesPerQuery.put(query, numOfOccurrences(originalText, query));
        }

        return numOfOccurrencesPerQuery;

    }



}
