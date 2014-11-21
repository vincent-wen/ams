package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.FlowConfig;
import com.paypal.api.payments.InputFields;
import com.paypal.api.payments.Presentation;

public class WebProfileList extends ArrayList<WebProfile> {

	private List<WebProfile> webProfiles = null;
	
	/**
	 * Default Constructor
	 */
	public WebProfileList() {
		webProfiles = new ArrayList<WebProfile>();
	}


	/**
	 * Setter for items
	 */
	public List<WebProfile> setWebProfileList(List<WebProfile> webProfiles) {
		this.webProfiles = webProfiles;
		return this.webProfiles;
	}

	/**
	 * Getter for items
	 */
	public List<WebProfile> getWebProfileLists() {
		return this.webProfiles;
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
