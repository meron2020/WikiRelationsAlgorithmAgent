package org.wikiRelationsAlgorithm.core;

import org.wikiRelationsAlgorithm.core.LinkWorkersTools.ArrayListSplitter;
import org.wikiRelationsAlgorithm.core.LinkWorkersTools.LinkInfoHandler;
import org.wikiRelationsAlgorithm.core.LinkWorkersTools.LinkInfoWorker;
import org.wikiRelationsAlgorithm.core.rankingMechanisms.MutualLinks;
import org.wikiRelationsAlgorithm.core.rankingMechanisms.NumOfOccurrences;
import org.wikiRelationsAlgorithm.core.rankingMechanisms.OrderOfAppearance;

import java.io.IOException;
import java.util.*;

public class RankingAlgorithm {

    public static Hashtable<String, String> titleToLinkConnection(String link, ArrayList<String> relatedLinks, Hashtable<String, Hashtable<String, Object>> relatedLinksInfo) throws IOException {
        Hashtable<String, String> titleToLinkTable = new Hashtable<>();

        for (String relatedLink : relatedLinks) {
            String title = (String) relatedLinksInfo.get(relatedLink).get("title");
            titleToLinkTable.put(title, relatedLink);
            System.out.println("[+] Added " + title + " to related links table.");
        }

        return titleToLinkTable;
    }

    public static Hashtable<String, Double> scoreEachQuery
            (String link, ArrayList<String> relatedLinks, Hashtable<String, String> titleToLink, Hashtable<String, Hashtable<String, Object>> relatedLinksInfo) throws IOException {

        HashMap<String, ArrayList<String>> queryLinksHash = new HashMap<>();

        String originalText = LinkInfoHandler.getLinkText(link);
        ArrayList<String> relatedTitles = new ArrayList<>();
        for (String relatedLink : relatedLinksInfo.keySet()) {
            String title = (String) relatedLinksInfo.get(relatedLink).get("title");
            relatedTitles.add(title);
            queryLinksHash.put(relatedLink, (ArrayList<String>) relatedLinksInfo.get(relatedLink).get("mutual links"));
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

            double score = 0.45 * Math.abs(1.3 * occurrences - 0.05*orderOfAppearanceIndex) + mutualLinkNum * 0.55;
            System.out.println(score);
            queryScore.put(queryEntry.getKey(), score);
        }

        return queryScore;
    }

    public static ArrayList<Hashtable<String, String>> rank(String originalLink, ArrayList<String> relatedLinks) throws IOException {

        ArrayList<ArrayList<String>> splitList = ArrayListSplitter.split(75, relatedLinks);
        ArrayList<LinkInfoWorker> linkInfoWorkers = new ArrayList<>();
        Hashtable<String, Hashtable<String, Object>> relatedLinksInfo = new Hashtable<>();
        for (ArrayList<String> array : splitList) {
            LinkInfoWorker linkInfoWorker = new LinkInfoWorker(array, relatedLinksInfo);
            linkInfoWorkers.add(linkInfoWorker);
            new Thread(linkInfoWorker).start();
        }

        while (linkInfoWorkers.size() != 0) {
            linkInfoWorkers.removeIf(LinkInfoWorker::isFinished);
        }

        Hashtable<String, String> titleToLink = titleToLinkConnection(originalLink, relatedLinks, relatedLinksInfo);


        Hashtable<String, Double> queryScore = scoreEachQuery(originalLink, relatedLinks, titleToLink, relatedLinksInfo);
        ArrayList<Map.Entry<String, Double>> listToSort = new ArrayList<>(queryScore.entrySet());
        listToSort.sort(Comparator.comparingDouble((Map.Entry<String, Double> o) -> Math.abs(o.getValue())).reversed());

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
