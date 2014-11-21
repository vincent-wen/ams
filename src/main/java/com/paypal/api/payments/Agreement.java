package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.MerchantPreferences;
import com.paypal.api.payments.OverrideChargeModel;
import java.util.List;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.Links;
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

public class Agreement  {

	/**
	 * Identifier of the agreement.
	 */
	private String id;

	/**
	 * Name of the agreement.
	 */
	private String name;

	/**
	 * Description of the agreement.
	 */
	private String description;

	/**
	 * Start date of the agreement. Date format yyyy-MM-dd z, as defined in [ISO8601](http://tools.ietf.org/html/rfc3339#section-5.6).
	 */
	private String startDate;

	/**
	 * Details of the buyer who is enrolling in this agreement. This information is gathered from execution of the approval URL.
	 */
	private Payer payer;

	/**
	 * Shipping address object of the agreement, which should be provided if it is different from the default address.
	 */
	private Address shippingAddress;

	/**
	 * Default merchant preferences from the billing plan are used, unless override preferences are provided here.
	 */
	private MerchantPreferences overrideMerchantPreferences;

	/**
	 * Array of override_charge_model for this agreement if needed to change the default models from the billing plan.
	 */
	private List<OverrideChargeModel> overrideChargeModels;

	/**
	 * Plan details for this agreement.
	 */
	private Plan plan;

	/**
	 * Date and time that this resource was created. Date format yyyy-MM-dd z, as defined in [ISO8601](http://tools.ietf.org/html/rfc3339#section-5.6).
	 */
	private String createTime;

	/**
	 * Date and time that this resource was updated. Date format yyyy-MM-dd z, as defined in [ISO8601](http://tools.ietf.org/html/rfc3339#section-5.6).
	 */
	private String updateTime;

	/**
	 * 
	 */
	private List<Links> links;

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
	public Agreement() {
	}

	/**
	 * Parameterized Constructor
	 */
	public Agreement(String name, String description, String startDate, Payer payer, Plan plan) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.payer = payer;
		this.plan = plan;
	}


	/**
	 * Setter for id
	 */
	public Agreement setId(String id) {
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
	 * Setter for name
	 */
	public Agreement setName(String name) {
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
	public Agreement setDescription(String description) {
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
	 * Setter for startDate
	 */
	public Agreement setStartDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	/**
	 * Getter for startDate
	 */
	public String getStartDate() {
		return this.startDate;
	}


	/**
	 * Setter for payer
	 */
	public Agreement setPayer(Payer payer) {
		this.payer = payer;
		return this;
	}

	/**
	 * Getter for payer
	 */
	public Payer getPayer() {
		return this.payer;
	}


	/**
	 * Setter for shippingAddress
	 */
	public Agreement setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
		return this;
	}

	/**
	 * Getter for shippingAddress
	 */
	public Address getShippingAddress() {
		return this.shippingAddress;
	}


	/**
	 * Setter for overrideMerchantPreferences
	 */
	public Agreement setOverrideMerchantPreferences(MerchantPreferences overrideMerchantPreferences) {
		this.overrideMerchantPreferences = overrideMerchantPreferences;
		return this;
	}

	/**
	 * Getter for overrideMerchantPreferences
	 */
	public MerchantPreferences getOverrideMerchantPreferences() {
		return this.overrideMerchantPreferences;
	}


	/**
	 * Setter for overrideChargeModels
	 */
	public Agreement setOverrideChargeModels(List<OverrideChargeModel> overrideChargeModels) {
		this.overrideChargeModels = overrideChargeModels;
		return this;
	}

	/**
	 * Getter for overrideChargeModels
	 */
	public List<OverrideChargeModel> getOverrideChargeModels() {
		return this.overrideChargeModels;
	}


	/**
	 * Setter for plan
	 */
	public Agreement setPlan(Plan plan) {
		this.plan = plan;
		return this;
	}

	/**
	 * Getter for plan
	 */
	public Plan getPlan() {
		return this.plan;
	}


	/**
	 * Setter for createTime
	 */
	public Agreement setCreateTime(String createTime) {
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
	public Agreement setUpdateTime(String updateTime) {
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
	 * Setter for links
	 */
	public Agreement setLinks(List<Links> links) {
		this.links = links;
		return this;
	}

	/**
	 * Getter for links
	 */
	public List<Links> getLinks() {
		return this.links;
	}


	/**
	 * Create a new billing agreement by passing the details for the agreement, including the name, description, start date, payer, and billing plan in the request JSON.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @return Agreement
	 * @throws PayPalRESTException
	 */
	public Agreement create(String accessToken) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return create(apiContext);
	}

	/**
	 * Create a new billing agreement by passing the details for the agreement, including the name, description, start date, payer, and billing plan in the request JSON.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @return Agreement
	 * @throws PayPalRESTException
	 */
	public Agreement create(APIContext apiContext) throws PayPalRESTException {
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
		String resourcePath = "v1/payments/billing-agreements";
		String payLoad = this.toJSON();
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, Agreement.class);
	}


	/**
	 * Execute a billing agreement after buyer approval by passing the payment token to the request URI.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @return Agreement
	 * @throws PayPalRESTException
	 */
	public Agreement execute(String accessToken) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return execute(apiContext);
	}

	/**
	 * Execute a billing agreement after buyer approval by passing the payment token to the request URI.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @return Agreement
	 * @throws PayPalRESTException
	 */
	public Agreement execute(APIContext apiContext) throws PayPalRESTException {
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
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/billing-agreements/{0}/agreement-execute";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, Agreement.class);
	}


	/**
	 * Retrieve details for a particular billing agreement by passing the ID of the agreement to the request URI.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param agreementId
	 *            String
	 * @return Agreement
	 * @throws PayPalRESTException
	 */
	public static Agreement get(String accessToken, String agreementId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return get(apiContext, agreementId);
	}

	/**
	 * Retrieve details for a particular billing agreement by passing the ID of the agreement to the request URI.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param agreementId
	 *            String
	 * @return Agreement
	 * @throws PayPalRESTException
	 */
	public static Agreement get(APIContext apiContext, String agreementId) throws PayPalRESTException {
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
		if (agreementId == null) {
			throw new IllegalArgumentException("agreementId cannot be null");
		}
		Object[] parameters = new Object[] {agreementId};
		String pattern = "v1/payments/billing-agreements/{0}";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad, Agreement.class);
	}


	/**
	 * Update details of a billing agreement, such as the description, shipping address, and start date, by passing the ID of the agreement to the request URI.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param patchRequest
	 *            PatchRequest
	 * @return Agreement
	 * @throws PayPalRESTException
	 */
	public Agreement update(String accessToken, PatchRequest patchRequest) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return update(apiContext, patchRequest);
	}

	/**
	 * Update details of a billing agreement, such as the description, shipping address, and start date, by passing the ID of the agreement to the request URI.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param patchRequest
	 *            PatchRequest
	 * @return Agreement
	 * @throws PayPalRESTException
	 */
	public Agreement update(APIContext apiContext, PatchRequest patchRequest) throws PayPalRESTException {
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
		if (patchRequest == null) {
			throw new IllegalArgumentException("patchRequest cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/billing-agreements/{0}";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = patchRequest.toJSON();
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.PATCH, resourcePath, payLoad, Agreement.class);
	}


	/**
	 * Suspend a particular billing agreement by passing the ID of the agreement to the request URI.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param agreementStateDescriptor
	 *            AgreementStateDescriptor
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void suspend(String accessToken, AgreementStateDescriptor agreementStateDescriptor) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		suspend(apiContext, agreementStateDescriptor);
		return;
	}

	/**
	 * Suspend a particular billing agreement by passing the ID of the agreement to the request URI.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param agreementStateDescriptor
	 *            AgreementStateDescriptor
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void suspend(APIContext apiContext, AgreementStateDescriptor agreementStateDescriptor) throws PayPalRESTException {
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
		if (agreementStateDescriptor == null) {
			throw new IllegalArgumentException("agreementStateDescriptor cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/billing-agreements/{0}/suspend";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = agreementStateDescriptor.toJSON();
		PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, null);
		return;
	}


	/**
	 * Reactivate a suspended billing agreement by passing the ID of the agreement to the appropriate URI. In addition, pass an agreement_state_descriptor object in the request JSON that includes a note about the reason for changing the state of the agreement and the amount and currency for the agreement.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param agreementStateDescriptor
	 *            AgreementStateDescriptor
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void reActivate(String accessToken, AgreementStateDescriptor agreementStateDescriptor) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		reActivate(apiContext, agreementStateDescriptor);
		return;
	}

	/**
	 * Reactivate a suspended billing agreement by passing the ID of the agreement to the appropriate URI. In addition, pass an agreement_state_descriptor object in the request JSON that includes a note about the reason for changing the state of the agreement and the amount and currency for the agreement.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param agreementStateDescriptor
	 *            AgreementStateDescriptor
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void reActivate(APIContext apiContext, AgreementStateDescriptor agreementStateDescriptor) throws PayPalRESTException {
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
		if (agreementStateDescriptor == null) {
			throw new IllegalArgumentException("agreementStateDescriptor cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/billing-agreements/{0}/re-activate";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = agreementStateDescriptor.toJSON();
		PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, null);
		return;
	}


	/**
	 * Cancel a billing agreement by passing the ID of the agreement to the request URI. In addition, pass an agreement_state_descriptor object in the request JSON that includes a note about the reason for changing the state of the agreement and the amount and currency for the agreement.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param agreementStateDescriptor
	 *            AgreementStateDescriptor
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void cancel(String accessToken, AgreementStateDescriptor agreementStateDescriptor) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		cancel(apiContext, agreementStateDescriptor);
		return;
	}

	/**
	 * Cancel a billing agreement by passing the ID of the agreement to the request URI. In addition, pass an agreement_state_descriptor object in the request JSON that includes a note about the reason for changing the state of the agreement and the amount and currency for the agreement.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param agreementStateDescriptor
	 *            AgreementStateDescriptor
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void cancel(APIContext apiContext, AgreementStateDescriptor agreementStateDescriptor) throws PayPalRESTException {
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
		if (agreementStateDescriptor == null) {
			throw new IllegalArgumentException("agreementStateDescriptor cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/billing-agreements/{0}/cancel";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = agreementStateDescriptor.toJSON();
		PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, null);
		return;
	}


	/**
	 * Bill an outstanding amount for an agreement by passing the ID of the agreement to the request URI. In addition, pass an agreement_state_descriptor object in the request JSON that includes a note about the reason for changing the state of the agreement and the amount and currency for the agreement.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param agreementStateDescriptor
	 *            AgreementStateDescriptor
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void billBalance(String accessToken, AgreementStateDescriptor agreementStateDescriptor) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		billBalance(apiContext, agreementStateDescriptor);
		return;
	}

	/**
	 * Bill an outstanding amount for an agreement by passing the ID of the agreement to the request URI. In addition, pass an agreement_state_descriptor object in the request JSON that includes a note about the reason for changing the state of the agreement and the amount and currency for the agreement.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param agreementStateDescriptor
	 *            AgreementStateDescriptor
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void billBalance(APIContext apiContext, AgreementStateDescriptor agreementStateDescriptor) throws PayPalRESTException {
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
		if (agreementStateDescriptor == null) {
			throw new IllegalArgumentException("agreementStateDescriptor cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/billing-agreements/{0}/bill-balance";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = agreementStateDescriptor.toJSON();
		PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, null);
		return;
	}


	/**
	 * Set the balance for an agreement by passing the ID of the agreement to the request URI. In addition, pass a common_currency object in the request JSON that specifies the currency type and value of the balance.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param currency
	 *            Currency
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void setBalance(String accessToken, Currency currency) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		setBalance(apiContext, currency);
		return;
	}

	/**
	 * Set the balance for an agreement by passing the ID of the agreement to the request URI. In addition, pass a common_currency object in the request JSON that specifies the currency type and value of the balance.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param currency
	 *            Currency
	 * @return 
	 * @throws PayPalRESTException
	 */
	public void setBalance(APIContext apiContext, Currency currency) throws PayPalRESTException {
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
		if (currency == null) {
			throw new IllegalArgumentException("currency cannot be null");
		}
		Object[] parameters = new Object[] {this.getId()};
		String pattern = "v1/payments/billing-agreements/{0}/set-balance";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = currency.toJSON();
		PayPalResource.configureAndExecute(apiContext, HttpMethod.POST, resourcePath, payLoad, null);
		return;
	}


	/**
	 * List transactions for a billing agreement by passing the ID of the agreement, as well as the start and end dates of the range of transactions to list, to the request URI.
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param agreementId
	 *            String
	 * @return AgreementTransactions
	 * @throws PayPalRESTException
	 */
	public static AgreementTransactions transactions(String accessToken, String agreementId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return transactions(apiContext, agreementId);
	}

	/**
	 * List transactions for a billing agreement by passing the ID of the agreement, as well as the start and end dates of the range of transactions to list, to the request URI.
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param agreementId
	 *            String
	 * @return AgreementTransactions
	 * @throws PayPalRESTException
	 */
	public static AgreementTransactions transactions(APIContext apiContext, String agreementId) throws PayPalRESTException {
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
		if (agreementId == null) {
			throw new IllegalArgumentException("agreementId cannot be null");
		}
		Object[] parameters = new Object[] {agreementId};
		String pattern = "v1/payments/billing-agreements/{0}/transactions";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad, AgreementTransactions.class);
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
