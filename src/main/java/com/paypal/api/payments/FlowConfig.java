package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;

public class FlowConfig  {

	/**
	 * Type of PayPal page to be displayed when a user lands on the PayPal site for checkout. Allowed values: `Billing` or `Login`. When set to `Billing`, the Non-PayPal account landing page is used. When set to `Login`, the PayPal account login landing page is used.
	 */
	private String landingPageType;

	/**
	 * The URL on the merchant site for transferring to after a bank transfer payment. Use this field only if you are using giropay or bank transfer payment methods in Germany.
	 */
	private String bankTxnPendingUrl;

	/**
	 * Default Constructor
	 */
	public FlowConfig() {
	}


	/**
	 * Setter for landingPageType
	 */
	public FlowConfig setLandingPageType(String landingPageType) {
		this.landingPageType = landingPageType;
		return this;
	}

	/**
	 * Getter for landingPageType
	 */
	public String getLandingPageType() {
		return this.landingPageType;
	}


	/**
	 * Setter for bankTxnPendingUrl
	 */
	public FlowConfig setBankTxnPendingUrl(String bankTxnPendingUrl) {
		this.bankTxnPendingUrl = bankTxnPendingUrl;
		return this;
	}

	/**
	 * Getter for bankTxnPendingUrl
	 */
	public String getBankTxnPendingUrl() {
		return this.bankTxnPendingUrl;
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
