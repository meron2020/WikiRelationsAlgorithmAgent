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

    public HeadMasterCommunicationHandler(RankingThreadList rankingThreadList) {
        this.rankingThreadList = rankingThreadList;
    }

    public void sendHeadMasterAgentInfo() throws IOException, InterruptedException {
        HashMap<String, Object> dataHashMap = new HashMap<>();
        dataHashMap.put("type", "Ranking");

        HTTPRequestsClass.sendPOSTRequest(dataHashMap);
    }

}
