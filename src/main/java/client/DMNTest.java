package client;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.model.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DMNTest extends AbstractKieServerConnector {

	// Remote deployment
    private static final String CONTAINER_ID = "LoanApprovalDMN_1.0.0-SNAPSHOT";
	
	// Spring boot
    // private static final String CONTAINER_ID = "LoanApprovalDMN-1.0.0-SNAPSHOT";
    
    private static final Logger log = LoggerFactory.getLogger(DMNTest.class);

    public static void main(String[] args) {
    	DMNTest client = new DMNTest();
        try {
        	DMNContext dmnContext = client.getDMNClient().newContext();
        	dmnContext.set("CreditScore", 800);
        	dmnContext.set("DTI", 0.41f);
        	
        	ServiceResponse<DMNResult> serverResp = 
        			client.getDMNClient().evaluateAll(CONTAINER_ID,
        			"https://kiegroup.org/dmn/_6E1E1D1D-5D41-4A33-8838-206C1E7B3584",
        			"loanapproval", dmnContext);
        	
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
