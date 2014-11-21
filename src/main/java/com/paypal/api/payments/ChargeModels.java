package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.Currency;

public class ChargeModels  {

	/**
	 * Identifier of the charge model.
	 */
	private String id;

	/**
	 * Type of charge model, possible values can be shipping/tax.
	 */
	private String type;

	/**
	 * Specific amount for this charge model.
	 */
	private Currency amount;

	/**
	 * Default Constructor
	 */
	public ChargeModels() {
	}

	/**
	 * Parameterized Constructor
	 */
	public ChargeModels(String type, Currency amount) {
		this.type = type;
		this.amount = amount;
	}


	/**
	 * Setter for id
	 */
	public ChargeModels setId(String id) {
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
	 * Setter for type
	 */
	public ChargeModels setType(String type) {
		this.type = type;
		return this;
	}

	/**
	 * Getter for type
	 */
	public String getType() {
		return this.type;
	}


	/**
	 * Setter for amount
	 */
	public ChargeModels setAmount(Currency amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Getter for amount
	 */
	public Currency getAmount() {
		return this.amount;
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
