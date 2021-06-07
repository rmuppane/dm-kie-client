package client;

import java.util.Set;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

public abstract class AbstractKieServerConnector {

	private Long timeout = 5000L;
	private String url;
	private String username;
	private String pwd;
	private KieServicesClient client;

	public AbstractKieServerConnector() {
		// Remote kie-server 
		/*this.url = "http://localhost:8080/kie-server/services/rest/server";
		this.username = "rhdmAdmin";
		this.pwd = "Pa$$w0rd";*/
		
		// Springboot kie-server 
		this.url = "http://localhost:8090/rest/server";
		this.username = "user";
		this.pwd = "user";
		
		KieServicesConfiguration config = KieServicesFactory
						.newRestConfiguration(url, username, pwd);
		config.setMarshallingFormat(MarshallingFormat.JSON);
		config.setTimeout(300000L);
		this.client = KieServicesFactory.newKieServicesClient(config);
	}
	
	// DMN client
    public DMNServicesClient getDMNClient() {
    	return this.client.getServicesClient(DMNServicesClient.class);
    }

    // Rule client
	public RuleServicesClient getRuleClient() {
		return this.client.getServicesClient(RuleServicesClient.class);
	}

	public AbstractKieServerConnector(String Url, String username, String password, Set<Class<?>> extraClassList,
			MarshallingFormat format) {

		this.url = Url;
		this.username = username;
		this.pwd = password;

		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(this.url, this.username, this.pwd);
		config.setTimeout(timeout);
		if (extraClassList != null) {

			config.addExtraClasses(extraClassList);
		}
		if (format == null) {

			config.setMarshallingFormat(MarshallingFormat.JSON);
		} else {

			config.setMarshallingFormat(format);
		}
		this.client = KieServicesFactory.newKieServicesClient(config);

	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public KieServicesClient getClient() {
		return client;
	}

	public void setClient(KieServicesClient client) {
		this.client = client;
	}

}
