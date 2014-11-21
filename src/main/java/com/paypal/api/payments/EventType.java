package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;
import com.paypal.core.rest.HttpMethod;
import com.paypal.core.rest.RESTUtil;
import com.paypal.core.rest.APIContext;
import com.paypal.core.Constants;
import com.paypal.sdk.info.SDKVersionImpl;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class EventType  {

	/**
	 * Unique event-type name.
	 */
	private String name;

	/**
	 * Human readable description of the event-type
	 */
	private String description;

	/**
	 * Returns the last request sent to the Service
	 *
	 * @return Last request sent to the server
	 */
	public static String getLastRequest() {
		return PayPalResource.getLastRequest();
	}

	/**
	 * Returns the last response returned by the Service
	 *
	 * @return Last response got from the Service
	 */
	public static String getLastResponse() {
		return PayPalResource.getLastResponse();
	}

	/**
	 * Initialize using InputStream(of a Properties file)
	 *
	 * @param is
	 *            InputStream
	 * @throws PayPalRESTException
	 * @return OAuthTokenCredential instance using client ID and client secret loaded from configuration.
	 */
	public static void initConfig(InputStream is) throws PayPalRESTException {
		PayPalResource.initConfig(is);
	}

	/**
	 * Initialize using a File(Properties file)
	 *
	 * @param file
	 *            File object of a properties entity
	 * @throws PayPalRESTException
	 * @return OAuthTokenCredential instance using client ID and client secret loaded from configuration.
	 */
	public static void initConfig(File file) throws PayPalRESTException {
		PayPalResource.initConfig(file);
	}

	/**
	 * Initialize using Properties
	 *
	 * @param properties
	 *            Properties object
	 * @return OAuthTokenCredential instance using client ID and client secret loaded from configuration.
	 */
	public static void initConfig(Properties properties) {
		PayPalResource.initConfig(properties);
	}
	/**
	 * Default Constructor
	 */
	public EventType() {
	}

	/**
	 * Parameterized Constructor
	 */
	public EventType(String name) {
		this.name = name;
	}


	/**
	 * Setter for name
	 */
	public EventType setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Getter for name
	 */
	public String getName() {
		return this.name;
	}


	/**
	 * Setter for description
	 */
	public EventType setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Getter for description
	 */
	public String getDescription() {
		return this.description;
	}


	/**
	 * Retrieves the list of events-types subscribed by the given Webhook.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param webhookId
	 *            String
	 * @return EventTypeList
	 * @throws PayPalRESTException
	 */
	public static EventTypeList subscribedEventTypes(String accessToken, String webhookId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return subscribedEventTypes(apiContext, webhookId);
	}

	/**
	 * Retrieves the list of events-types subscribed by the given Webhook.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param webhookId
	 *            String
	 * @return EventTypeList
	 * @throws PayPalRESTException
	 */
	public static EventTypeList subscribedEventTypes(APIContext apiContext, String webhookId) throws PayPalRESTException {
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
		if (webhookId == null) {
			throw new IllegalArgumentException("webhookId cannot be null");
		}
		Object[] parameters = new Object[] {webhookId};
		String pattern = "v1/notifications/webhooks/{0}/event-types";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad, EventTypeList.class);
	}


	/**
	 * Retrieves the master list of available Webhooks events-types resources for any webhook to subscribe to.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @return EventTypeList
	 * @throws PayPalRESTException
	 */
	public static EventTypeList availableEventTypes(String accessToken) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return availableEventTypes(apiContext);
	}

	/**
	 * Retrieves the master list of available Webhooks events-types resources for any webhook to subscribe to.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @return EventTypeList
	 * @throws PayPalRESTException
	 */
	public static EventTypeList availableEventTypes(APIContext apiContext) throws PayPalRESTException {
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
		String resourcePath = "v1/notifications/webhooks-event-types";
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad, EventTypeList.class);
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
