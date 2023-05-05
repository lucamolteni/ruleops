package org.drools.ruleops.extension.deployment;

import javax.inject.Inject;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.GeneratedClassBuildItem;
import io.quarkus.deployment.builditem.GeneratedResourceBuildItem;
import io.quarkus.deployment.pkg.builditem.OutputTargetBuildItem;
import io.quarkus.gizmo.ClassCreator;
import io.quarkus.gizmo.ClassOutput;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;

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
                .className("org.drools.cliexample.TopCommand")
                .build();

        classCreator.addAnnotation(io.quarkus.picocli.runtime.annotations.TopCommand.class);



            AnnotationInstance annotation =
                    AnnotationInstance.builder(DotName.createSimple(picocli.CommandLine.Command.class))
                            .add("mixinStandardHelpOptions", true)
//                            .add("subcommands", new Class[]{
//                                    Class.forName("FindNamespaceCommand.class"),
//                                    Class.forName("FindPodCommand.class")
//                            })
                            .build();
            classCreator.addAnnotation(annotation);



        classCreator.close();
    }

}
