package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.Currency;

public class AgreementStateDescriptor  {

	/**
	 * Reason for changing the state of the agreement.
	 */
	private String note;

	/**
	 * The amount and currency of the agreement.
	 */
	private Currency amount;

	/**
	 * Default Constructor
	 */
	public AgreementStateDescriptor() {
	}

	/**
	 * Parameterized Constructor
	 */
	public AgreementStateDescriptor(Currency amount) {
		this.amount = amount;
	}


	/**
	 * Setter for note
	 */
	public AgreementStateDescriptor setNote(String note) {
		this.note = note;
		return this;
	}

	/**
	 * Getter for note
	 */
	public String getNote() {
		return this.note;
	}


	/**
	 * Setter for amount
	 */
	public AgreementStateDescriptor setAmount(Currency amount) {
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
