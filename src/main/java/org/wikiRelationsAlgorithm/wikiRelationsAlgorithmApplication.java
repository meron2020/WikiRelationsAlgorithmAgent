package org.wikiRelationsAlgorithm;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.wikiRelationsAlgorithm.core.HeadMasterCommunications.HeadMasterCommunicationHandler;
import org.wikiRelationsAlgorithm.core.RankingThreadList;
import org.wikiRelationsAlgorithm.resources.HeadMasterCommsResource;
import org.wikiRelationsAlgorithm.resources.RankingResource;

import java.io.IOException;

public class wikiRelationsAlgorithmApplication extends Application<wikiRelationsAlgorithmConfiguration> {

    public static void main(final String[] args) throws Exception {
        new wikiRelationsAlgorithmApplication().run(args);
    }

    @Override
    public String getName() {
        return "wikiRelationsAlgorithm";
    }

    @Override
    public void initialize(final Bootstrap<wikiRelationsAlgorithmConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final wikiRelationsAlgorithmConfiguration configuration,
                    final Environment environment) throws IOException, InterruptedException {
        // TODO: implement application

        RankingThreadList rankingThreadList = new RankingThreadList();
        HeadMasterCommunicationHandler headMasterCommunicationHandler = new HeadMasterCommunicationHandler(rankingThreadList);
        headMasterCommunicationHandler.sendHeadMasterAgentInfo("9000");
        environment.jersey().register(new RankingResource(rankingThreadList));
        environment.jersey().register(new HeadMasterCommsResource(rankingThreadList));
    }

}
