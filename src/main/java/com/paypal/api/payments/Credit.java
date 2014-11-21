package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;

public class Credit  {

	/**
	 * Unique identifier of credit resource.
	 */
	private String id;

	/**
	 * specifies type of credit
	 */
	private String type;

	/**
	 * URI to the associated terms
	 */
	private String terms;

	/**
	 * Default Constructor
	 */
	public Credit() {
	}

	/**
	 * Parameterized Constructor
	 */
	public Credit(String type) {
		this.type = type;
	}


	/**
	 * Setter for id
	 */
	public Credit setId(String id) {
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
	public Credit setType(String type) {
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
	 * Setter for terms
	 */
	public Credit setTerms(String terms) {
		this.terms = terms;
		return this;
	}

	/**
	 * Getter for terms
	 */
	public String getTerms() {
		return this.terms;
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
