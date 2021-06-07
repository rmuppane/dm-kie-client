package client;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.model.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.loanapprovaldrl.LoanApplication;

public class DRLRuleTest extends AbstractKieServerConnector {

	// Remote deployment
    // private static final String CONTAINER_ID = "LoanApprovalDRL_1.0.0-SNAPSHOT";
    
    // Spring boot
    private static final String CONTAINER_ID = "LoanApprovalDRL-1.0.0-SNAPSHOT";
    
    private static final Logger log = LoggerFactory.getLogger(DRLRuleTest.class);

    public static void main(String[] args) {
    	DRLRuleTest client = new DRLRuleTest();
        try {
        	LoanApplication loanApp = new LoanApplication();
        	loanApp.setCreditScore(800);
        	loanApp.setDti(0.41f);
        	
        	KieCommands kieCommands = KieServices.Factory.get().getCommands();
        	List<Command> commandList = new ArrayList<Command>();
        	commandList.add(CommandFactory.newInsert(loanApp, "loanApp"));
        	
        	// Fire all rules:
        	commandList.add(kieCommands.newFireAllRules("numberOfFiredRules"));
        	BatchExecutionCommand batch = kieCommands.newBatchExecution(commandList, "defaultKieSession");
        	
        	ServiceResponse<ExecutionResults> executeResponse =
        			client.getRuleClient().executeCommandsWithResults(CONTAINER_ID, batch);
        	System.out.println("number of fired rules:" +
        	executeResponse.getResult().getValue("numberOfFiredRules"));
        	
        	LoanApplication loanAppUpdated = (LoanApplication)executeResponse.getResult().getValue("loanApp");
        	
        	log.info("Result is {} ", loanAppUpdated.getLoanApproval());

        } catch (Exception e) {
            e.printStackTrace();

        } 
    }

}
