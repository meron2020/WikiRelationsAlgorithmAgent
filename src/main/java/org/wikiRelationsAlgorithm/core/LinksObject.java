package org.wikiRelationsAlgorithm.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class LinksObject {
    @JsonProperty("originalLink")
    public String originalLink;
    @JsonProperty("links")
    public ArrayList<String> links;
    @JsonProperty("id")
    public Integer id;

    public String getOriginalLink() {
        return originalLink;
    }

    public Integer getId() {
        return id;
    }

    public ArrayList<String> getLinks() {
        return links;
    }
}
