package org.drools.ruleops.extension.deployment;

import java.io.PrintWriter;

import javax.inject.Inject;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanDefiningAnnotationBuildItem;
import io.quarkus.arc.deployment.GeneratedBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.pkg.builditem.OutputTargetBuildItem;
import io.quarkus.gizmo.ClassCreator;
import io.quarkus.gizmo.ClassOutput;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import picocli.CommandLine;

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
    BeanDefiningAnnotationBuildItem commandBeanDefiningAnnotation() {
        return new BeanDefiningAnnotationBuildItem(DotName.createSimple(CommandLine.Command.class.getName()));
    }

    @BuildStep
    BeanDefiningAnnotationBuildItem topcommandAnnotation() {
        return new BeanDefiningAnnotationBuildItem(DotName.createSimple(TopCommand.class.getName()));
    }

    @BuildStep
    protected void build(
            CombinedIndexBuildItem combinedIndexBuildItemConsumer,
            BuildProducer<GeneratedBeanBuildItem> generatedBean,
                         BuildProducer<AdditionalBeanBuildItem> additionalBean) {

        System.out.println("++++ Init: " + TOP_COMMAND_NAME);

        ClassOutput classOutput = (name, data) -> {
            System.out.println("Generating classoutput: " + name);
            generatedBean.produce(new GeneratedBeanBuildItem(name, data));
//            additionalBean.produce(new AdditionalBeanBuildItem(name)); this doesn't work as it tries to index it
        };

        ClassCreator classCreator = ClassCreator.builder()
                .classOutput(classOutput)
                .className(TOP_COMMAND_NAME)
                .interfaces(Runnable.class)
                .build();

        classCreator.getMethodCreator("run", "void").returnVoid();

        classCreator.addAnnotation(io.quarkus.picocli.runtime.annotations.TopCommand.class);

        AnnotationInstance annotation =
                AnnotationInstance.builder(DotName.createSimple(picocli.CommandLine.Command.class))
                        .add("mixinStandardHelpOptions", true)

                        .build();
        classCreator.addAnnotation(annotation);

        classCreator.close();

    }
}
