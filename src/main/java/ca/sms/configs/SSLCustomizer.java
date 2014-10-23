package ca.sms.configs;

import java.io.FileNotFoundException;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class SSLCustomizer implements EmbeddedServletContainerCustomizer {
	// get data from application.properties
	@Value("${spring.ssl.port}")
	private int port;
	@Value("${spring.ssl.protocol}")
	private String protocol;
	@Value("${spring.ssl.clientAuth}")
	private boolean clientAuth;
	@Value("${spring.ssl.enabled}")
	private boolean enabled;
	@Value("${spring.ssl.schema}")
	private String schema;
	@Value("${spring.ssl.secure}")
	private boolean secure;
	@Value("${spring.ssl.keystoreAlias}")
	private String keystoreAlias;
	@Value("${spring.ssl.keystorePassword}")
	private String keystorePassword;
	@Value("${spring.ssl.keystoreFile}")
	private String keystoreFile;
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		if(container instanceof TomcatEmbeddedServletContainerFactory) {
			((TomcatEmbeddedServletContainerFactory) container).addConnectorCustomizers(
					new TomcatConnectorCustomizer() {
						
						@Override
						public void customize(Connector connector) {
							connector.setPort(port);
							connector.setSecure(secure);
							connector.setScheme(schema);
							connector.setAttribute("keyAlias", keystoreAlias);
							connector.setAttribute("keystorePass", keystorePassword);
							try {
								connector.setAttribute("keystoreFile", ResourceUtils.getFile(keystoreFile).getAbsolutePath());
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							connector.setAttribute("clientAuth", clientAuth);
							connector.setAttribute("sslProtocal", protocol);
							connector.setAttribute("SSLEnabled", enabled);
						}
					});
		}
	}
}