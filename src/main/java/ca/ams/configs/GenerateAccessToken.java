package ca.ams.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;

@Component
public class GenerateAccessToken {
	// ###AccessToken
	// Retrieve the access token from
	// OAuthTokenCredential by passing in
	// ClientID and ClientSecret
	@Value("${paypal.clientID}")
	private String clientId;
	@Value("${paypal.clientSecret}")
	private String clientSecret;
	
	public String getAccessToken() throws PayPalRESTException {
		return new OAuthTokenCredential(clientId, clientSecret)
				.getAccessToken();
	}
}
