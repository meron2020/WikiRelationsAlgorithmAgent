package org.wikiRelationsAlgorithm;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.wikiRelationsAlgorithm.resources.RankingResource;

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
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register(new RankingResource());
    }

}
