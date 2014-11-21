package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;
import com.paypal.api.payments.EventType;

import java.util.List;

public class EventTypeList  {

	/**
	 * A list of Webhooks event-types
	 */
	private List<EventType> eventTypes;

	/**
	 * Default Constructor
	 */
	public EventTypeList() {
	}


	/**
	 * Setter for eventTypes
	 */
	public EventTypeList setEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
		return this;
	}

	/**
	 * Getter for eventTypes
	 */
	public List<EventType> getEventTypes() {
		return this.eventTypes;
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
