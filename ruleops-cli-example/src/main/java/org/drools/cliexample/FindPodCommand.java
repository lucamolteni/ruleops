package org.drools.cliexample;

import java.util.List;

import javax.inject.Inject;

import org.drools.ruleops.LevelTrigger;
import org.drools.ruleops.RuleOps;
import org.drools.ruleops.TraceListener;
import org.drools.ruleops.cli.DroolsPicoCLICommand;
import org.drools.ruleops.model.Advice;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.StatelessKieSession;
import picocli.CommandLine;
@CommandLine.Command(name = "pod", description = "Find a pod with a certain name")
public class FindPodCommand extends DroolsPicoCLICommand {

    @CommandLine.Parameters(index = "0", description = "The Pod name to search for")
    String name;

    @Inject
    public FindPodCommand(LevelTrigger levelTrigger, KieRuntimeBuilder runtimeBuilder) {
        super(levelTrigger, runtimeBuilder);
    }

    @Override
    protected String getKieBaseName() {
        return "findpod";
    }

    @Override
    protected String[] args() {
        return new String[] {name};
    }
}