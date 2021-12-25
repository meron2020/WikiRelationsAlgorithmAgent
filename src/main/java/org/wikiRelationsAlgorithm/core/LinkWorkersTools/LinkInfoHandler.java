package org.wikiRelationsAlgorithm.core.LinkWorkersTools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class LinkInfoHandler {
    public static Document getLinkInfo(String link) throws IOException {
        return Jsoup.connect(link).get();
    }

    public static String getLinkTitle(Document doc) {
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

}
