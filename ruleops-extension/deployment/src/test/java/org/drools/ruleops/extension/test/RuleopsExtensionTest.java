package org.drools.ruleops.extension.test;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;

import io.quarkus.test.QuarkusUnitTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class RuleopsExtensionTest {

    // Start unit test with your extension loaded
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addAsResource("META-INF/kmodule.xml", "src/main/resources/META-INF/kmodule.xml")
                    .addAsDirectories("rules/examplerules/findpod/", "src/main/resources/rules/examplerules/findpod/")
                    .addAsResource("rules/examplerules/findpod/find-pod.drl", "src/main/resources/rules/examplerules/findpod/find-pod.drl")
            );

    @Test
    public void writeYourOwnUnitTest() throws ClassNotFoundException {

        Instance<?> topCommand = CDI.current().select(Class.forName("org.drools.cliexample.ExampleTopCommand"));
        // Write your unit tests here - see the testing extension guide https://quarkus.io/guides/writing-extensions#testing-extensions for more information
        Assertions.assertNotNull(topCommand);
    }
}
