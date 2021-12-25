package org.wikiRelationsAlgorithm.core;

import java.util.ArrayList;
import java.util.Hashtable;

public class RankingThreadList {
    public ArrayList<RankingThread> rankingThreads = new ArrayList<>();

    public int rankingThreadsListSize() {
        return rankingThreads.size();
    }

    public void addRankingThread(LinksObject linksObject) {
        RankingThread rankingThread = new RankingThread(linksObject);
        this.rankingThreads.add(rankingThread);
        new Thread(rankingThread).start();
    }


    public ArrayList<Object> getAllFinishedRankings() {
        ArrayList<Object> finished = new ArrayList<>();

        ArrayList<RankingThread> finishedThreads = new ArrayList<>();
        for (RankingThread rankingThread: rankingThreads) {
            if (rankingThread.isFinished()) {
                finishedThreads.add(rankingThread);
                Hashtable<String, Object> links = new Hashtable<>();
                links.put("Links", rankingThread.getRankedTitleToLink());
                links.put("id", rankingThread.linksObject.getId());
                links.put("Original Link", rankingThread.linksObject.getOriginalLink());
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
