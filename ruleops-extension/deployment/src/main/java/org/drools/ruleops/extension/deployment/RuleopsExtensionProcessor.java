package org.drools.ruleops.extension.deployment;

import java.nio.charset.StandardCharsets;

import io.quarkus.arc.deployment.GeneratedBeanBuildItem;
import io.quarkus.arc.deployment.UnremovableBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.GeneratedClassBuildItem;
import io.quarkus.deployment.builditem.GeneratedResourceBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.pkg.builditem.OutputTargetBuildItem;
import io.quarkus.gizmo.ClassCreator;
import io.quarkus.gizmo.ClassOutput;

import javax.inject.Inject;
import javax.inject.Singleton;

class RuleopsExtensionProcessor {

    private static final String FEATURE = "ruleops-extension";

    @Inject
    OutputTargetBuildItem outputTargetBuildItem;

    @BuildStep
    FeatureBuildItem feature() {
        // parse kmodule.xml
        // for each kbase, generate one command file

        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    protected void build(BuildProducer<GeneratedClassBuildItem> generatedBean,
                         BuildProducer<GeneratedResourceBuildItem> generatedResourceBuildItemBuildProducer) {

        System.out.println("Generating Prova.class in " + outputTargetBuildItem.getOutputDirectory());

        ClassOutput classOutput = (name, data) -> {
            System.out.println("Generating classoutput: " + name);
            generatedBean.produce(new GeneratedClassBuildItem(true, name, data));
        };

        ClassCreator classCreator = ClassCreator.builder()
                .classOutput(classOutput)
                .className("org.drools.Prova")
                .build();

        classCreator.addAnnotation(Singleton.class);

        classCreator.getFieldCreator("field1", String.class);

        classCreator.getMethodCreator("method", String.class);

        classCreator.close();

        GeneratedResourceBuildItem pippo = new GeneratedResourceBuildItem("pippo", "pippo".getBytes(StandardCharsets.UTF_8));
        generatedResourceBuildItemBuildProducer.produce(pippo);
    }

}
