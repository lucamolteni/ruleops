package org.drools.cliexample;

import javax.inject.Inject;

import org.drools.ruleops.LevelTrigger;
import org.drools.ruleops.cli.DroolsPicoCLICommand;
import org.kie.api.runtime.KieRuntimeBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "namespace", description = "Find a namespace with a certain name")
public class FindNamespaceCommand extends DroolsPicoCLICommand {

    @CommandLine.Parameters(index = "0", description = "The Namespace name to search for")
    String name;

    @Inject
    public FindNamespaceCommand(LevelTrigger levelTrigger,
                                KieRuntimeBuilder runtimeBuilder) {
        super(levelTrigger, runtimeBuilder);
    }

    @Override
    protected String getKieBaseName() {
        return "findnamespace";
    }

    @Override
    protected String[] args() {
        return new String[]{name};
    }
}