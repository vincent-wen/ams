package com.paypal.api.payments;

import com.paypal.core.Constants;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.HttpMethod;
import com.paypal.core.rest.JSONFormatter;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;
import com.paypal.core.rest.RESTUtil;
import com.paypal.sdk.info.SDKVersionImpl;
import com.paypal.api.payments.Webhook;

import java.util.HashMap;
import java.util.List;

public class WebhookList  {

	/**
	 * A list of Webhooks
	 */
	private List<Webhook> webhooks;

	/**
	 * Default Constructor
	 */
	public WebhookList() {
	}


	/**
	 * Setter for webhooks
	 */
	public WebhookList setWebhooks(List<Webhook> webhooks) {
		this.webhooks = webhooks;
		return this;
	}

	/**
	 * Getter for webhooks
	 */
	public List<Webhook> getWebhooks() {
		return this.webhooks;
	}
	
	/**
	 * Retrieves all Webhooks for the application associated with access token.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @return WebhookList
	 * @throws PayPalRESTException
	 */
	public WebhookList getAll(String accessToken) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return getAll(apiContext);
	}

	/**
	 * Retrieves all Webhooks for the application associated with access token.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @return WebhookList
	 * @throws PayPalRESTException
	 */
	public WebhookList getAll(APIContext apiContext) throws PayPalRESTException {
		if (apiContext == null) {
			throw new IllegalArgumentException("APIContext cannot be null");
		}
		if (apiContext.getAccessToken() == null || apiContext.getAccessToken().trim().length() <= 0) {
			throw new IllegalArgumentException("AccessToken cannot be null or empty");
		}
		if (apiContext.getHTTPHeaders() == null) {
			apiContext.setHTTPHeaders(new HashMap<String, String>());
		}
		apiContext.getHTTPHeaders().put(Constants.HTTP_CONTENT_TYPE_HEADER, Constants.HTTP_CONTENT_TYPE_JSON);
		apiContext.setSdkVersion(new SDKVersionImpl());

		Object[] parameters = new Object[] {};
		String pattern = "v1/notifications/webhooks/";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad, WebhookList.class);
	}

	/**
	 * Returns a JSON string corresponding to object state
	 *
	 * @return JSON representation
	 */
	public String toJSON() {
		return JSONFormatter.toJSON(this);
	}

	@Override
	public String toString() {
		return toJSON();
	}
}
