package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import java.util.List;
import com.paypal.api.payments.TransactionBase;

public class Transaction extends TransactionBase {

	/**
	 * Additional transactions for complex payment scenarios.
	 */
	private List<Transaction> transactions;

	/**
	 * Default Constructor
	 */
	public Transaction() {
	}


	/**
	 * Setter for transactions
	 */
	public Transaction setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
		return this;
	}

	/**
	 * Getter for transactions
	 */
	public List<Transaction> getTransactions() {
		return this.transactions;
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
