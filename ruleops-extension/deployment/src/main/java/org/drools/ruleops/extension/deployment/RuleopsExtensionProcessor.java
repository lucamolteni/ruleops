package org.drools.ruleops.extension.deployment;

import java.util.HashMap;

import javax.inject.Inject;

import io.quarkus.arc.deployment.GeneratedBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.pkg.builditem.OutputTargetBuildItem;
import io.quarkus.gizmo.ClassCreator;
import io.quarkus.gizmo.ClassOutput;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;

class RuleopsExtensionProcessor {

    private static final String FEATURE = "ruleops-extension";
    public static final String TOP_COMMAND_NAME = "org.drools.cliexample.ExampleTopCommand";
    public static final String COMMAND_1_NAME = "org.drools.cliexample.Command1";

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
                .className(COMMAND_1_NAME)
                .interfaces(Runnable.class)
                .build()) {

            classCreator.getMethodCreator("run", "void").returnVoid();

            AnnotationInstance annotation =
                    AnnotationInstance.builder(DotName.createSimple(picocli.CommandLine.Command.class))
                            .add("name", "commandname")
                            .build();
            classCreator.addAnnotation(annotation);
        }

        try (ClassCreator classCreator = ClassCreator.builder()
                .classOutput(classOutput)
                .className(TOP_COMMAND_NAME)
                .build()) {

            classCreator.addAnnotation(io.quarkus.picocli.runtime.annotations.TopCommand.class);

            DotName[] empty = new DotName[]{};
            ClassInfo subCommandName = ClassInfo.create(DotName.createSimple(COMMAND_1_NAME),
                                                        DotName.createSimple(""),
                                                        (short) 0, empty, new HashMap<>(), true);


            AnnotationInstance annotation =
                    AnnotationInstance.builder(DotName.createSimple(picocli.CommandLine.Command.class))
                            .add("mixinStandardHelpOptions", true)
                            .add("subcommands", new ClassInfo[]{ subCommandName })
                            .build();
            classCreator.addAnnotation(annotation);
        }
    }
}
