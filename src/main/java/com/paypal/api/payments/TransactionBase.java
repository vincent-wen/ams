package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.RelatedResources;
import java.util.List;
import com.paypal.api.payments.CartBase;

public class TransactionBase extends CartBase {

	/**
	 * List of financial transactions (Sale, Authorization, Capture, Refund) related to the payment.
	 */
	private List<RelatedResources> relatedResources;

	/**
	 * Default Constructor
	 */
	public TransactionBase() {
	}


	/**
	 * Setter for relatedResources
	 */
	public TransactionBase setRelatedResources(List<RelatedResources> relatedResources) {
		this.relatedResources = relatedResources;
		return this;
	}

	/**
	 * Getter for relatedResources
	 */
	public List<RelatedResources> getRelatedResources() {
		return this.relatedResources;
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
