package org.drools.ruleops;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

// not a: @QuarkusIntegrationTest, this is not to test the result of the build, this is IT with minikube/kind
@QuarkusTest
public class MutinyIT {
    @Inject
    LevelTrigger levelTrigger;

    @Test
    public void smokeTestLevelTrigger() {
        levelTrigger.fetchFromKubernetes();
    }
}
