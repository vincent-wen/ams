package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;

public class PaymentOptions  {

	/**
	 * Payment method requested for this purchase unit
	 */
	private String allowedPaymentMethod;

	/**
	 * Default Constructor
	 */
	public PaymentOptions() {
	}


	/**
	 * Setter for allowedPaymentMethod
	 */
	public PaymentOptions setAllowedPaymentMethod(String allowedPaymentMethod) {
		this.allowedPaymentMethod = allowedPaymentMethod;
		return this;
	}

	/**
	 * Getter for allowedPaymentMethod
	 */
	public String getAllowedPaymentMethod() {
		return this.allowedPaymentMethod;
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
