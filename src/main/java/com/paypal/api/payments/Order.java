package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.Amount;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.HttpMethod;
import com.paypal.core.rest.RESTUtil;
import com.paypal.core.rest.QueryParameters;
import com.paypal.core.rest.APIContext;
import com.paypal.core.Constants;
import com.paypal.core.SDKVersion;
import com.paypal.sdk.info.SDKVersionImpl;
import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class Order  {

	/**
	 * Identifier of the order transaction.
	 */
	private String id;

	/**
	 * Identifier to the purchase unit associated with this object
	 */
	private String purchaseUnitReferenceId;

	/**
	 * Time the resource was created in UTC ISO8601 format.
	 */
	private String createTime;

	/**
	 * Time the resource was last updated in UTC ISO8601 format.
	 */
	private String updateTime;

	/**
	 * Amount being collected.
	 */
	private Amount amount;

	/**
	 * specifies payment mode of the transaction
	 */
	private String paymentMode;

	/**
	 * State of the order transaction.
	 */
	private String state;

	/**
	 * Protection Eligibility of the Payer 
	 */
	private String protectionEligibility;

	/**
	 * Protection Eligibility Type of the Payer 
	 */
	private String protectionEligibilityType;

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
	public static OAuthTokenCredential initConfig(InputStream is) throws PayPalRESTException {
		return PayPalResource.initConfig(is);
	}

	/**
	 * Initialize using a File(Properties file)
	 *
	 * @param file
	 *            File object of a properties entity
	 * @throws PayPalRESTException
	 * @return OAuthTokenCredential instance using client ID and client secret loaded from configuration.
	 */
	public static OAuthTokenCredential initConfig(File file) throws PayPalRESTException {
		return PayPalResource.initConfig(file);
	}

	/**
	 * Initialize using Properties
	 *
	 * @param properties
	 *            Properties object
	 * @return OAuthTokenCredential instance using client ID and client secret loaded from configuration.
	 */
	public static OAuthTokenCredential initConfig(Properties properties) {
		return PayPalResource.initConfig(properties);
	}
	/**
	 * Default Constructor
	 */
	public Order() {
	}

	/**
	 * Parameterized Constructor
	 */
	public Order(String id, Amount amount, String state) {
		this.id = id;
		this.amount = amount;
		this.state = state;
	}


	/**
	 * Setter for id
	 */
	public Order setId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * Getter for id
	 */
	public String getId() {
		return this.id;
	}


	/**
	 * Setter for purchaseUnitReferenceId
	 */
	public Order setPurchaseUnitReferenceId(String purchaseUnitReferenceId) {
		this.purchaseUnitReferenceId = purchaseUnitReferenceId;
		return this;
	}

	/**
	 * Getter for purchaseUnitReferenceId
	 */
	public String getPurchaseUnitReferenceId() {
		return this.purchaseUnitReferenceId;
	}


	/**
	 * Setter for createTime
	 */
	public Order setCreateTime(String createTime) {
		this.createTime = createTime;
		return this;
	}

	/**
	 * Getter for createTime
	 */
	public String getCreateTime() {
		return this.createTime;
	}


	/**
	 * Setter for updateTime
	 */
	public Order setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	/**
	 * Getter for updateTime
	 */
	public String getUpdateTime() {
		return this.updateTime;
	}


	/**
	 * Setter for amount
	 */
	public Order setAmount(Amount amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Getter for amount
	 */
	public Amount getAmount() {
		return this.amount;
	}


	/**
	 * Setter for paymentMode
	 */
	public Order setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
		return this;
	}

	/**
	 * Getter for paymentMode
	 */
	public String getPaymentMode() {
		return this.paymentMode;
	}


	/**
	 * Setter for state
	 */
	public Order setState(String state) {
		this.state = state;
		return this;
	}

	/**
	 * Getter for state
	 */
	public String getState() {
		return this.state;
	}


	/**
	 * Setter for protectionEligibility
	 */
	public Order setProtectionEligibility(String protectionEligibility) {
		this.protectionEligibility = protectionEligibility;
		return this;
	}

	/**
	 * Getter for protectionEligibility
	 */
	public String getProtectionEligibility() {
		return this.protectionEligibility;
	}


	/**
	 * Setter for protectionEligibilityType
	 */
	public Order setProtectionEligibilityType(String protectionEligibilityType) {
		this.protectionEligibilityType = protectionEligibilityType;
		return this;
	}

	/**
	 * Getter for protectionEligibilityType
	 */
	public String getProtectionEligibilityType() {
		return this.protectionEligibilityType;
	}


	/**
	 * Obtain the Order resource for the given identifier.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param orderId
	 *            String
	 * @return Order
	 * @throws PayPalRESTException
	 */
	public static Order get(String accessToken, String orderId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return get(apiContext, orderId);
	}

	/**
	 * Obtain the Order resource for the given identifier.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param orderId
	 *            String
	 * @return Order
	 * @throws PayPalRESTException
	 */
	public static Order get(APIContext apiContext, String orderId) throws PayPalRESTException {
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
		if (orderId == null) {
			throw new IllegalArgumentException("orderId cannot be null");
		}
		Object[] parameters = new Object[] {orderId};
		String pattern = "v1/payments/orders/{0}";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad, Order.class);
	}


	/**
	 * Creates (and processes) a new Capture Transaction added as a related resource.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param capture
	 *            Capture
	 * @return Capture
	 * @throws PayPalRESTException
	 */
	public Capture capture(String accessToken, Capture capture) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return capture(apiContext, capture);
	}

	/**
	 * Creates (and processes) a new Capture Transaction added as a related resource.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param capture
	 *            Capture
	 * @return Capture
	 * @throws PayPalRESTException
	 */
	public Capture capture(APIContext apiContext, Capture capture) throws PayPalRESTException {
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
		if (this.getId() == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		if (capture == null) {
			throw new IllegalArgumentException("capture cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/orders/{0}/capture";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = capture.toJSON();
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, Capture.class);
	}


	/**
	 * Voids (cancels) an Order.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @return Order
	 * @throws PayPalRESTException
	 */
	public Order doVoid(String accessToken) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return doVoid(apiContext);
	}

	/**
	 * Voids (cancels) an Order.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @return Order
	 * @throws PayPalRESTException
	 */
	public Order doVoid(APIContext apiContext) throws PayPalRESTException {
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
		if (this.getId() == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/orders/{0}/do-void";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, Order.class);
	}


	/**
	 * Creates an authorization on an order
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @return Authorization
	 * @throws PayPalRESTException
	 */
	public Authorization authorize(String accessToken) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return authorize(apiContext);
	}

	/**
	 * Creates an authorization on an order
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @return Authorization
	 * @throws PayPalRESTException
	 */
	public Authorization authorize(APIContext apiContext) throws PayPalRESTException {
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
		if (this.getId() == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/orders/{0}/authorization";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = this.toJSON();
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, Authorization.class);
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
