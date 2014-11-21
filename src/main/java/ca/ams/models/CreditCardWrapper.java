package ca.ams.models;

import com.paypal.api.payments.CreditCard;

public class CreditCardWrapper {

	private String type;
	private String firstName;
	private String lastName;
	private String number;
	private String expireMonth;
	private String expireYear;
	private String cvv2;
	private String amount;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getExpireMonth() {
		return expireMonth;
	}
	public void setExpireMonth(String expireMonth) {
		this.expireMonth = expireMonth;
	}
	public String getExpireYear() {
		return expireYear;
	}
	public void setExpireYear(String expireYear) {
		this.expireYear = expireYear;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public CreditCard convertToCreditCard() {
		CreditCard creditCard = new CreditCard(number, type, 
				Integer.parseInt(expireMonth), Integer.parseInt(expireYear));
		creditCard.setFirstName(firstName);
		creditCard.setLastName(lastName);
		creditCard.setCvv2(Integer.parseInt(cvv2));
		return creditCard;
	}
	
}
