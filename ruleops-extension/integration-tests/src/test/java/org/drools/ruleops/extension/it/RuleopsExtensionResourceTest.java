package org.drools.ruleops.extension.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class RuleopsExtensionResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/ruleops-extension")
                .then()
                .statusCode(200)
                .body(is("Hello ruleops-extension"));
    }
}
