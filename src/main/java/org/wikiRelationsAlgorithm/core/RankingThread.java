package org.wikiRelationsAlgorithm.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class RankingThread extends Thread {
    ArrayList<Hashtable<String, String>> rankedTitleToLink;
    LinksObject linksObject;
    boolean finished = false;
    boolean started = false;

    public RankingThread(LinksObject linksObject) {
        this.linksObject = linksObject;
    }

    public void run() {
        this.started = true;
        try {
            String originalLink = this.linksObject.getOriginalLink();
            ArrayList<String> relatedLinks = this.linksObject.getLinks();
            this.rankedTitleToLink = RankingAlgorithm.rank(originalLink, relatedLinks);

            this.finished = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Hashtable<String, String>> getRankedTitleToLink() {
        return this.rankedTitleToLink;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isStarted() {
        return started;
    }
}
