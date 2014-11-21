package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.Address;

public class ShippingAddress extends Address {

	/**
	 * Address ID assigned in PayPal system.
	 */
	private String id;

	/**
	 * Name of the recipient at this address.
	 */
	private String recipientName;

	/**
	 * Default shipping address of the Payer.
	 */
	private Boolean defaultAddress;

	/**
	 * Default Constructor
	 */
	public ShippingAddress() {
	}

	/**
	 * Parameterized Constructor
	 */
	public ShippingAddress(String recipientName) {
		this.recipientName = recipientName;
	}


	/**
	 * Setter for id
	 */
	public ShippingAddress setId(String id) {
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
	 * Setter for recipientName
	 */
	public ShippingAddress setRecipientName(String recipientName) {
		this.recipientName = recipientName;
		return this;
	}

	/**
	 * Getter for recipientName
	 */
	public String getRecipientName() {
		return this.recipientName;
	}


	/**
	 * Setter for defaultAddress
	 */
	public ShippingAddress setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
		return this;
	}

	/**
	 * Getter for defaultAddress
	 */
	public Boolean getDefaultAddress() {
		return this.defaultAddress;
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
