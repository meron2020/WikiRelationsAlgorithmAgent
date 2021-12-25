package org.wikiRelationsAlgorithm.resources;

import org.wikiRelationsAlgorithm.core.LinksObject;
import org.wikiRelationsAlgorithm.core.RankingThreadList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Hashtable;

@Path("/ranking")
public class RankingResource {
    RankingThreadList rankingThreadList;

    public RankingResource(RankingThreadList rankingThreadList) {
        this.rankingThreadList = rankingThreadList;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postLinks(LinksObject linksObject) {
        this.rankingThreadList.addRankingThread(linksObject);
        return "Links posted successfully";
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Object> getFinishedRankings() {
        return this.rankingThreadList.getAllFinishedRankings();
    }
}
