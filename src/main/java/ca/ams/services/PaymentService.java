package ca.ams.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.configs.GenerateAccessToken;
import ca.ams.models.CreditCardWrapper;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.PayPalRESTException;

@Component
public class PaymentService {
	
	@Autowired
	private GenerateAccessToken accessTokenGenerator;
	
	private static final String cardNumberRegex = "^[0-9]{16}$";
	private static final String expireMonthRegex = "^[0-9]{2}$";
	private static final String expireYearRegex = "^[0-9]{4}$";
	private static final String cvv2Regex = "^[0-9]{3}$";
	private static final String nameRegex = "^[a-zA-Z]+$";
	private static final String typeRegex = "visa|mastercard|discover|amex";
	private static final String amountRegex = "^[0-9]+(\\.[0-9]{2})?$";
		
	public boolean isAmountFormatValid(String amount) {
		return amount.matches(amountRegex);
	}
	
	public Payment payByCreditCard(CreditCardWrapper creditCardWrapper) {
		// Verification
		if(!creditCardWrapper.getNumber().matches(cardNumberRegex) ||
			!creditCardWrapper.getExpireMonth().matches(expireMonthRegex) ||
			!creditCardWrapper.getExpireYear().matches(expireYearRegex) ||
			!creditCardWrapper.getFirstName().matches(nameRegex) ||
			!creditCardWrapper.getLastName().matches(nameRegex) ||
			!creditCardWrapper.getCvv2().matches(cvv2Regex) ||
			!creditCardWrapper.getType().matches(typeRegex)) return null;
		
		CreditCard creditCard = creditCardWrapper.convertToCreditCard();
		
		// ###Amount
		// Let's you specify a payment amount.
		Amount amount = new Amount();
		amount.setCurrency("CAD");
		// Total must be equal to sum of shipping, tax and subtotal.
		amount.setTotal(creditCardWrapper.getAmount());

		// ###Transaction
		// A transaction defines the contract of a
		// payment - what is the payment for and who
		// is fulfilling it. Transaction is created with
		// a `Payee` and `Amount` types
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("Concordia tuition payment transaction via direct credit card service by PayPal.");

		// The Payment creation API requires a list of
		// Transaction; add the created `Transaction`
		// to a List
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// ###FundingInstrument
		// A resource representing a Payeer's funding instrument.
		// Use a Payer ID (A unique identifier of the payer generated
		// and provided by the facilitator. This is required when
		// creating or using a tokenized funding instrument)
		// and the `CreditCardDetails`
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);

		// The Payment creation API requires a list of
		// FundingInstrument; add the created `FundingInstrument`
		// to a List
		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		// ###Payer
		// A resource representing a Payer that funds a payment
		// Use the List of `FundingInstrument` and the Payment Method
		// as 'credit_card'
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");

		// ###Payment
		// A Payment Resource; create one using
		// the above types and intent as 'sale'
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		try {
			// ###AccessToken
			// Retrieve the access token from
			// OAuthTokenCredential by passing in
			// ClientID and ClientSecret
			// It is not mandatory to generate Access Token on a per call basis.
			// Typically the access token can be generated once and
			// reused within the expiry window
			String accessToken = accessTokenGenerator.getAccessToken();

			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			APIContext apiContext = new APIContext(accessToken);
			// Use this variant if you want to pass in a request id
			// that is meaningful in your application, ideally
			// a order id.
			/*
			 * String requestId = Long.toString(System.nanoTime(); APIContext
			 * apiContext = new APIContext(accessToken, requestId ));
			 */

			// Create a payment by posting to the APIService
			// using a valid AccessToken
			// The return object contains the status;
			return payment.create(apiContext);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Payment payByPayPalAccount(String amountReq) {		
		Amount amount = new Amount();
		amount.setCurrency("CAD");
		amount.setTotal(amountReq);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("Concordia tuition payment transaction via PayPal account.");

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("https://ams.vincent-tech.com/api/payment/paypal/paypal-account?success=false");
		redirectUrls.setReturnUrl("https://ams.vincent-tech.com/api/payment/paypal/paypal-account?success=true");
		payment.setRedirectUrls(redirectUrls);

		try {
			String accessToken = accessTokenGenerator.getAccessToken();
			APIContext apiContext = new APIContext(accessToken);
			return payment.create(apiContext);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Payment executePayPalAccount(String paymentId, String payerID) {
		
		try {
			String accessToken = accessTokenGenerator.getAccessToken();
			APIContext apiContext = new APIContext(accessToken);
			
			Payment payment = new Payment();
			payment.setId(paymentId);
			PaymentExecution paymentExecute = new PaymentExecution();
			paymentExecute.setPayerId(payerID);
			return payment.execute(apiContext, paymentExecute);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return null;
	}
}
