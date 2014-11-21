package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.AgreementTransaction;
import java.util.List;

public class AgreementTransactions  {

	/**
	 * Array of agreement_transaction object.
	 */
	private List<AgreementTransaction> agreementTransactionList;

	/**
	 * Default Constructor
	 */
	public AgreementTransactions() {
	}


	/**
	 * Setter for agreementTransactionList
	 */
	public AgreementTransactions setAgreementTransactionList(List<AgreementTransaction> agreementTransactionList) {
		this.agreementTransactionList = agreementTransactionList;
		return this;
	}

	/**
	 * Getter for agreementTransactionList
	 */
	public List<AgreementTransaction> getAgreementTransactionList() {
		return this.agreementTransactionList;
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
