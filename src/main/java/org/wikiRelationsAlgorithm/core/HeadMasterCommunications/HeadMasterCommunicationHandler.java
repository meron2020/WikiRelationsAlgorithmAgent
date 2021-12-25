package org.wikiRelationsAlgorithm.core.HeadMasterCommunications;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wikiRelationsAlgorithm.core.RankingThreadList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Hashtable;

public class HeadMasterCommunicationHandler {
    RankingThreadList rankingThreadList;
    String selfUrl;

    public HeadMasterCommunicationHandler(RankingThreadList rankingThreadList, String selfUrl) {
        this.rankingThreadList = rankingThreadList;
        this.selfUrl = selfUrl;
    }

    public void sendHeadMasterAgentInfo() {
        HashMap<String, Object> dataHashMap = new HashMap<>();
        dataHashMap.put("agent_url", selfUrl);
        dataHashMap.put("thread_list_size", rankingThreadList.rankingThreadsListSize());

    }



}
