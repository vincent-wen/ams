package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.FundingInstrument;
import java.util.List;
import com.paypal.api.payments.PayerInfo;

public class Payer  {

	/**
	 * Payment method being used - PayPal Wallet payment, Bank Direct Debit  or Direct Credit card.
	 */
	private String paymentMethod;

	/**
	 * Status of Payer PayPal Account.
	 */
	private String status;

	/**
	 * List of funding instruments from where the funds of the current payment come from. Typically a credit card.
	 */
	private List<FundingInstrument> fundingInstruments;

	/**
	 * Id of user selected funding option for the payment. 'OneOf' funding_instruments or funding_option_id to be present 
	 */
	private String fundingOptionId;

	/**
	 * Information related to the Payer. 
	 */
	private PayerInfo payerInfo;

	/**
	 * Default Constructor
	 */
	public Payer() {
	}

	/**
	 * Parameterized Constructor
	 */
	public Payer(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	/**
	 * Setter for paymentMethod
	 */
	public Payer setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
		return this;
	}

	/**
	 * Getter for paymentMethod
	 */
	public String getPaymentMethod() {
		return this.paymentMethod;
	}


	/**
	 * Setter for status
	 */
	public Payer setStatus(String status) {
		this.status = status;
		return this;
	}

	/**
	 * Getter for status
	 */
	public String getStatus() {
		return this.status;
	}


	/**
	 * Setter for fundingInstruments
	 */
	public Payer setFundingInstruments(List<FundingInstrument> fundingInstruments) {
		this.fundingInstruments = fundingInstruments;
		return this;
	}

	/**
	 * Getter for fundingInstruments
	 */
	public List<FundingInstrument> getFundingInstruments() {
		return this.fundingInstruments;
	}


	/**
	 * Setter for fundingOptionId
	 */
	public Payer setFundingOptionId(String fundingOptionId) {
		this.fundingOptionId = fundingOptionId;
		return this;
	}

	/**
	 * Getter for fundingOptionId
	 */
	public String getFundingOptionId() {
		return this.fundingOptionId;
	}


	/**
	 * Setter for payerInfo
	 */
	public Payer setPayerInfo(PayerInfo payerInfo) {
		this.payerInfo = payerInfo;
		return this;
	}

	/**
	 * Getter for payerInfo
	 */
	public PayerInfo getPayerInfo() {
		return this.payerInfo;
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
