package org.drools.ruleops.cli;

import java.util.List;

import org.drools.ruleops.LevelTrigger;
import org.drools.ruleops.RuleOps;
import org.drools.ruleops.TraceListener;
import org.drools.ruleops.model.Advice;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.StatelessKieSession;

public abstract class DroolsPicoCLICommand implements Runnable {

    private final LevelTrigger levelTrigger;
    private final KieRuntimeBuilder runtimeBuilder;

    protected abstract String getKieBaseName();

    protected abstract String[] args();

    public DroolsPicoCLICommand(LevelTrigger levelTrigger, KieRuntimeBuilder runtimeBuilder) {
        this.levelTrigger = levelTrigger;
        this.runtimeBuilder = runtimeBuilder;
    }

    RuleOps createRuleOps() {
        StatelessKieSession ksession = runtimeBuilder.getKieBase(getKieBaseName()).newStatelessKieSession();
        ksession.addEventListener(new TraceListener());
        return new RuleOps(levelTrigger, ksession);
    }

    @Override
    public void run() {
        List<Advice> advices = createRuleOps().evaluateAllRulesStateless(args());

        for (Advice a : advices) {
            System.out.println("");
            System.out.printf("  💡 %s%n", a.title());
            System.out.println("");
            System.out.println(a.description());
        }

        System.out.println("");
    }
}
