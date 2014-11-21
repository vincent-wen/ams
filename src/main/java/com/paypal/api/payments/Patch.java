package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;

public class Patch  {

	/**
	 * Patch operation to perform.Value required for add & remove operation can be any JSON value.
	 */
	private String op;

	/**
	 * string containing a JSON-Pointer value that references a location within the target document (the target location) where the operation is performed.
	 */
	private String path;

	/**
	 * Default Constructor
	 */
	public Patch() {
	}

	/**
	 * Parameterized Constructor
	 */
	public Patch(String op, String path) {
		this.op = op;
		this.path = path;
	}


	/**
	 * Setter for op
	 */
	public Patch setOp(String op) {
		this.op = op;
		return this;
	}

	/**
	 * Getter for op
	 */
	public String getOp() {
		return this.op;
	}


	/**
	 * Setter for path
	 */
	public Patch setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Getter for path
	 */
	public String getPath() {
		return this.path;
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
