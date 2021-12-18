package org.wikiRelationsAlgorithm.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.wikiRelationsAlgorithm.core.rankingMechanisms.MutualLinks;
import org.wikiRelationsAlgorithm.core.rankingMechanisms.NumOfOccurrences;
import org.wikiRelationsAlgorithm.core.rankingMechanisms.OrderOfAppearance;

import java.io.IOException;
import java.util.*;

public class RankingAlgorithm {
    public static Document getLinkInfo(String link) throws IOException {
        return Jsoup.connect(link).get();
    }

    public static String getLinkTitle(String link) throws IOException {
        Document doc = getLinkInfo(link);
        String wikipediaTitle = doc.title();
        return wikipediaTitle.replace(" - Wikipedia", "");
    }

    public static String getLinkText(String link) throws IOException {
        Document doc = getLinkInfo(link);
        return doc.text();
    }

    public static ArrayList<String> getAllLinks(Document document) {
        Elements aElements = document.select("a");
        ArrayList<String> allLinks = new ArrayList<>();
        for (Element a : aElements) {
            String link = "https://en.wikipedia.org" + a.attr("href");
            allLinks.add(link);
        }
        return allLinks;
    }

    public static Hashtable<String, String> titleToLinkConnection(String link, ArrayList<String> relatedLinks) throws IOException {
        Hashtable<String, String> titleToLinkTable = new Hashtable<>();
        for (String relatedLink : relatedLinks) {
            String title = getLinkTitle(relatedLink);
            titleToLinkTable.put(title, relatedLink);
        }

        return titleToLinkTable;
    }

    public static Hashtable<String, Double> scoreEachQuery
            (String link, ArrayList<String> relatedLinks, Hashtable<String, String> titleToLink) throws IOException {
        String originalText = getLinkText(link);
        ArrayList<String> relatedTitles = new ArrayList<>();
        HashMap<String, ArrayList<String>> queryLinksHash = new HashMap<>();
        for (String relatedLink : relatedLinks) {
            queryLinksHash.put(relatedLink, getAllLinks(getLinkInfo(relatedLink)));
            String title = getLinkTitle(relatedLink);
            relatedTitles.add(title);
        }
        Hashtable<String, Integer> occurrencesHashTable = NumOfOccurrences.checkAllQueries(originalText, relatedTitles);

        Hashtable<String, Integer> mutualLinksArray = MutualLinks.findAllMutualLinks(relatedLinks, queryLinksHash);
        ArrayList<Map.Entry<String, Integer>> orderOfAppearance = OrderOfAppearance.getOrderOfAppearance
                (originalText, relatedTitles);

        Hashtable<String, Double> queryScore = new Hashtable<>();

        for (Map.Entry<String, Integer> queryEntry: orderOfAppearance) {
            Integer occurrences = occurrencesHashTable.get(queryEntry.getKey());
            Integer mutualLinkNum = mutualLinksArray.get(titleToLink.get(queryEntry.getKey()));
            int orderOfAppearanceIndex = orderOfAppearance.indexOf(queryEntry);

            double score = 0.45 * (1.3 * occurrences - 0.2*orderOfAppearanceIndex) + mutualLinkNum * 0.55;
            System.out.println(score);
            queryScore.put(queryEntry.getKey(), score);
        }

        return queryScore;
    }

    public static ArrayList<Hashtable<String, String>> rank(String originalLink, ArrayList<String> relatedLinks) throws IOException {
        Hashtable<String, String> titleToLink = titleToLinkConnection(originalLink, relatedLinks);


        Hashtable<String, Double> queryScore = scoreEachQuery(originalLink, relatedLinks, titleToLink);
        ArrayList<Map.Entry<String, Double>> listToSort = new ArrayList<>(queryScore.entrySet());
        listToSort.sort(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        }.reversed());

        ArrayList<Map.Entry<String, Double>> topTen;
        if (listToSort.size() > 10) {
            topTen = new ArrayList<>(listToSort.subList(0, 10));
        }
        else {
            topTen = new ArrayList<>(listToSort.subList(0, listToSort.size()));
        }
        ArrayList<Hashtable<String, String>> topTenTitleToLink = new ArrayList<>();
        for (Map.Entry<String, Double> query: topTen) {
            Hashtable<String, String> titleToLinkMap = new Hashtable<>();
            titleToLinkMap.put(query.getKey(), titleToLink.get(query.getKey()));
            topTenTitleToLink.add(titleToLinkMap);
        }
        return topTenTitleToLink;
    }

}
