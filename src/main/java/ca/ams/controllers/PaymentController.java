package ca.ams.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.api.payments.Payment;

import ca.ams.models.CreditCardWrapper;
import ca.ams.models.Role;
import ca.ams.models.Student;
import ca.ams.models.User;
import ca.ams.services.PaymentService;
import ca.ams.services.UserService;

@Controller
public class PaymentController {
	@Autowired
	private UserService userService;
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value="/api/payment/paypal/direct-credit-card", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> payPalDirectCreditCard(@RequestBody CreditCardWrapper creditCardWrapper) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_STUDENT) {
			Student student = (Student) user;
			
			if(!paymentService.isAmountFormatValid(creditCardWrapper.getAmount()))
				return new ResponseEntity<String>("Error: Your format of amount is invalid.", HttpStatus.NOT_ACCEPTABLE);
			BigDecimal amountToPay = new BigDecimal(creditCardWrapper.getAmount());
			if(amountToPay.compareTo(student.getTuition()) == 1)
				return new ResponseEntity<String>("Error: Your payment amount should not be larger than your left tuition to pay.", HttpStatus.NOT_ACCEPTABLE);
			
			Payment createdPayment = paymentService.payByCreditCard(creditCardWrapper);
			if(createdPayment != null && createdPayment.getState().equals("approved")) {
				BigDecimal newTuition = student.getTuition().subtract(amountToPay);
				student.setTuition(newTuition);
				userService.save(student);
				return new ResponseEntity<Payment>(createdPayment, HttpStatus.OK);
			}
			return new ResponseEntity<String>("Error: Payment failed. Please check your credit card's information and try again.", HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Error: Only Student Account can proceed a payment.", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/api/payment/paypal/paypal-account", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> payPalAccount(@RequestBody String amountReq) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_STUDENT) {
			Student student = (Student) user;
			
			if(!paymentService.isAmountFormatValid(amountReq))
				return new ResponseEntity<String>("Error: Your format of amount is invalid.", HttpStatus.NOT_ACCEPTABLE);
			BigDecimal amountToPay = new BigDecimal(amountReq);
			if(amountToPay.compareTo(student.getTuition()) == 1)
				return new ResponseEntity<String>("Error: Your payment amount should not be larger than your left tuition to pay.", HttpStatus.NOT_ACCEPTABLE);
			
			Payment createdPayment = paymentService.payByPayPalAccount(amountReq);
			
			if(createdPayment != null && createdPayment.getState().equals("created")) {
				return new ResponseEntity<Payment>(createdPayment, HttpStatus.OK);
			}
			return new ResponseEntity<String>("Error: Payment failed. Please check your credit card's information and try again.", HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Error: Only Student Account can proceed a payment.", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/api/payment/paypal/paypal-account", method = RequestMethod.GET)
	public String executePayPalAccount(@RequestParam boolean success, 
			@RequestParam(required = false) String paymentId, @RequestParam String token, @RequestParam(required = false) String PayerID) {
		if(!success) return "redirect:/payment/paypal/payment_cancel";
		
		Payment createdPayment = paymentService.executePayPalAccount(paymentId, PayerID);
		if(createdPayment != null && createdPayment.getState().equals("approved")) {
			User user = userService.getCurrentUser();
			if(user.getRole() != Role.ROLE_STUDENT)
				return "redirect:/payment/paypal/user_invalid";
			Student student = (Student) user;
			
			String amount = createdPayment.getTransactions().get(0).getAmount().getTotal();
			BigDecimal newTuition = student.getTuition().subtract(new BigDecimal(amount));
			student.setTuition(newTuition);
			userService.save(student);
			return "redirect:/payment/paypal/payment_success";
		}
		return "redirect:/payment/paypal/payment_failure";
	}
}
