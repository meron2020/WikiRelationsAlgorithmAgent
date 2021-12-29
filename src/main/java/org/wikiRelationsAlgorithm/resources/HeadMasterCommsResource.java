package org.wikiRelationsAlgorithm.resources;

import org.wikiRelationsAlgorithm.core.RankingThreadList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/communication")
public class HeadMasterCommsResource {
    RankingThreadList rankingThreadList;

    public HeadMasterCommsResource(RankingThreadList rankingThreadList) {
        this.rankingThreadList = rankingThreadList;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Integer getRankingListSize() {
        return this.rankingThreadList.rankingThreadsListSize();
    }

}
