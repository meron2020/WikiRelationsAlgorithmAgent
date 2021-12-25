package org.wikiRelationsAlgorithm.core.HeadMasterCommunications;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class HTTPRequestsClass {
    static String headMasterUrl = "http://127.0.0.1:8080/agents/";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void sendPOSTRequest(HashMap<String, Object> data)
            throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();

        String yourBody = mapper.writeValueAsString(data);


        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(yourBody))
                .uri(URI.create(headMasterUrl))
                .header("Content-Type", "application/json")
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    }


}
