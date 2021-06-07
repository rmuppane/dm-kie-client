package client;

import java.util.HashMap;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.model.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoanpreApprovalDMNTest extends AbstractKieServerConnector {

	// Remote deployment
    //private static final String CONTAINER_ID = "loanpreapprovaldmn_1.0.0-SNAPSHOT";
	
	// Spring boot
    private static final String CONTAINER_ID = "loanpreapprovaldmn-1.0.0-SNAPSHOT";
    
    private static final Logger log = LoggerFactory.getLogger(LoanpreApprovalDMNTest.class);

    public static void main(String[] args) {
    	DMNTest client = new DMNTest();
        try {
        	
        	DMNContext dmnContext = client.getDMNClient().newContext();
        	dmnContext.set("CreditScore", 670);
        	HashMap<String, Object> hs = new HashMap<String, Object>();
        	hs.put("monthlyIncome", 50000);
            hs.put("additionalExpenses", 10000);
        	dmnContext.set("Applicant",  hs);
        	
        	HashMap<String, Object> ls = new HashMap<String, Object>();
        	ls. put("interestRate", 3);
        	ls.put("principal", 200000);
        	ls.put("term", 60);
        	dmnContext.set("Loan",ls);
        	
        	ServiceResponse<DMNResult> serverResp = 
        			client.getDMNClient().evaluateAll(CONTAINER_ID,
        			"https://kiegroup.org/dmn/_515ECC75-150F-4814-9E39-466584FD0221",
        			"loanpreapprovaldmn", dmnContext);
        	
        	DMNResult dmnResult = serverResp.getResult(); 
			for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
				log.info( "Decision: '" + dr.getDecisionName() + "', " + "Result: "
						+ dr.getResult());
			}
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
