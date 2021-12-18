package org.wikiRelationsAlgorithm.core;

import java.util.ArrayList;
import java.util.Hashtable;

public class RankingThreadList {
    public ArrayList<RankingThread> rankingThreads = new ArrayList<>();

    public void addRankingThread(LinksObject linksObject) {
        RankingThread rankingThread = new RankingThread(linksObject);
        this.rankingThreads.add(rankingThread);
        rankingThread.start();
    }


    public ArrayList<Object> getAllFinishedRankings() {
        ArrayList<Object> finished = new ArrayList<>();

        ArrayList<RankingThread> finishedThreads = new ArrayList<>();
        for (RankingThread rankingThread: rankingThreads) {
            if (rankingThread.isFinished()) {
                finishedThreads.add(rankingThread);
                Hashtable<String, ArrayList<Hashtable<String,String>>> links = new Hashtable<>();
                links.put("Links", rankingThread.getRankedTitleToLink());
                Hashtable<String, Integer> idTable = new Hashtable<>();
                idTable.put("id", rankingThread.linksObject.getId());
                Hashtable<String, String> original = new Hashtable<>();
                original.put("Original Link", rankingThread.linksObject.getOriginalLink());
                finished.add(idTable);
                finished.add(original);
                finished.add(links);

            }
        }

        for (RankingThread rankingThread: finishedThreads) {
            rankingThreads.remove(rankingThread);
        }

        System.out.println(rankingThreads.size());

        return finished;
    }

}
