package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.BankAccount;

public class ExtendedBankAccount extends BankAccount {

	/**
	 * Identifier of the direct debit mandate to validate. Currently supported only for EU bank accounts(SEPA).
	 */
	private String mandateReferenceNumber;

	/**
	 * Default Constructor
	 */
	public ExtendedBankAccount() {
	}


	/**
	 * Setter for mandateReferenceNumber
	 */
	public ExtendedBankAccount setMandateReferenceNumber(String mandateReferenceNumber) {
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
