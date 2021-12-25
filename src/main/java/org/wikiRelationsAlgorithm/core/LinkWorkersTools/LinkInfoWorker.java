package org.wikiRelationsAlgorithm.core.LinkWorkersTools;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class LinkInfoWorker implements Runnable {
    boolean finished = false;
    ArrayList<String> links;
    Hashtable<String, Hashtable<String, Object>> relatedLinksInfo;

    public LinkInfoWorker(ArrayList<String> links, Hashtable<String, Hashtable<String, Object>> relatedLinksInfo){
        this.links = links;
        this.relatedLinksInfo = relatedLinksInfo;
    }

    public boolean isFinished() {
        return finished;
    }

    public void getAllInfoForLinks() throws IOException {
        for (String relatedLink: links) {
            Hashtable<String, Object> infoHashtable = new Hashtable<>();
            Document doc = LinkInfoHandler.getLinkInfo(relatedLink);
            infoHashtable.put("title", LinkInfoHandler.getLinkTitle(doc));
            infoHashtable.put("mutual links", LinkInfoHandler.getAllLinks(doc));
            this.relatedLinksInfo.put(relatedLink, infoHashtable);
        }

    }

    @Override
    public void run() {
        try {
            getAllInfoForLinks();
            this.finished = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
