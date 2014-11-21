package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;

public class NameValuePair  {

	/**
	 * Key for the name value pair.  The value name types should be correlated 
	 */
	private String name;

	/**
	 * Value for the name value pair.
	 */
	private String value;

	/**
	 * Default Constructor
	 */
	public NameValuePair() {
	}

	/**
	 * Parameterized Constructor
	 */
	public NameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}


	/**
	 * Setter for name
	 */
	public NameValuePair setName(String name) {
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
	 * Setter for value
	 */
	public NameValuePair setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Getter for value
	 */
	public String getValue() {
		return this.value;
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
