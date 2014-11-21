package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;

public class BankToken  {

	/**
	 * ID of a previously saved Bank resource using /vault/bank API.
	 */
	private String bankId;

	/**
	 * The unique identifier of the payer used when saving this bank using /vault/bank API.
	 */
	private String externalCustomerId;

	/**
	 * Identifier of the direct debit mandate to validate. Currently supported only for EU bank accounts(SEPA).
	 */
	private String mandateReferenceNumber;

	/**
	 * Default Constructor
	 */
	public BankToken() {
	}

	/**
	 * Parameterized Constructor
	 */
	public BankToken(String bankId, String externalCustomerId) {
		this.bankId = bankId;
		this.externalCustomerId = externalCustomerId;
	}


	/**
	 * Setter for bankId
	 */
	public BankToken setBankId(String bankId) {
		this.bankId = bankId;
		return this;
	}

	/**
	 * Getter for bankId
	 */
	public String getBankId() {
		return this.bankId;
	}


	/**
	 * Setter for externalCustomerId
	 */
	public BankToken setExternalCustomerId(String externalCustomerId) {
		this.externalCustomerId = externalCustomerId;
		return this;
	}

	/**
	 * Getter for externalCustomerId
	 */
	public String getExternalCustomerId() {
		return this.externalCustomerId;
	}


	/**
	 * Setter for mandateReferenceNumber
	 */
	public BankToken setMandateReferenceNumber(String mandateReferenceNumber) {
		this.mandateReferenceNumber = mandateReferenceNumber;
		return this;
	}

	/**
	 * Getter for mandateReferenceNumber
	 */
	public String getMandateReferenceNumber() {
		return this.mandateReferenceNumber;
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
