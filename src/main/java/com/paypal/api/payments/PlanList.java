package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.Plan;
import java.util.List;
import com.paypal.api.payments.Links;

public class PlanList  {

	/**
	 * Array of billing plans.
	 */
	private List<Plan> plans;

	/**
	 * Total number of items.
	 */
	private String totalItems;

	/**
	 * Total number of pages.
	 */
	private String totalPages;

	/**
	 * 
	 */
	private List<Links> links;

	/**
	 * Default Constructor
	 */
	public PlanList() {
	}


	/**
	 * Setter for plans
	 */
	public PlanList setPlans(List<Plan> plans) {
		this.plans = plans;
		return this;
	}

	/**
	 * Getter for plans
	 */
	public List<Plan> getPlans() {
		return this.plans;
	}


	/**
	 * Setter for totalItems
	 */
	public PlanList setTotalItems(String totalItems) {
		this.totalItems = totalItems;
		return this;
	}

	/**
	 * Getter for totalItems
	 */
	public String getTotalItems() {
		return this.totalItems;
	}


	/**
	 * Setter for totalPages
	 */
	public PlanList setTotalPages(String totalPages) {
		this.totalPages = totalPages;
		return this;
	}

	/**
	 * Getter for totalPages
	 */
	public String getTotalPages() {
		return this.totalPages;
	}


	/**
	 * Setter for links
	 */
	public PlanList setLinks(List<Links> links) {
		this.links = links;
		return this;
	}

	/**
	 * Getter for links
	 */
	public List<Links> getLinks() {
		return this.links;
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
