package org.drools.ruleops.extension.deployment;

import javax.inject.Inject;

import io.quarkus.arc.deployment.GeneratedBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.pkg.builditem.OutputTargetBuildItem;
import io.quarkus.gizmo.ClassCreator;
import io.quarkus.gizmo.ClassOutput;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;

class RuleopsExtensionProcessor {

    private static final String FEATURE = "ruleops-extension";
    public final String TOP_COMMAND_NAME = "org.drools.cliexample.ExampleTopCommand";

    @Inject
    OutputTargetBuildItem outputTargetBuildItem;

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    protected void build(
            BuildProducer<GeneratedBeanBuildItem> generatedBean) {

        System.out.println("++++ Init: " + TOP_COMMAND_NAME);

        ClassOutput classOutput = (name, data) -> {
            System.out.println("Generating classoutput: " + name);
            generatedBean.produce(new GeneratedBeanBuildItem(name, data));
        };

        try (ClassCreator classCreator = ClassCreator.builder()
                .classOutput(classOutput)
                .className(TOP_COMMAND_NAME)
                .interfaces(Runnable.class)
                .build()) {

            classCreator.getMethodCreator("run", "void").returnVoid();

            classCreator.addAnnotation(io.quarkus.picocli.runtime.annotations.TopCommand.class);

            AnnotationInstance annotation =
                    AnnotationInstance.builder(DotName.createSimple(picocli.CommandLine.Command.class))
                            .add("mixinStandardHelpOptions", true)
                            .build();
            classCreator.addAnnotation(annotation);
        }
    }
}
