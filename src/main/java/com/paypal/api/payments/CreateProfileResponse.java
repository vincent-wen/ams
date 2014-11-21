package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;

public class CreateProfileResponse  {

	/**
	 * ID of the payment web experience profile.
	 */
	private String id;

	/**
	 * Default Constructor
	 */
	public CreateProfileResponse() {
	}


	/**
	 * Setter for id
	 */
	public CreateProfileResponse setId(String id) {
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
