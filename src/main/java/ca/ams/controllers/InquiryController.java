package ca.ams.controllers;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.ams.models.*;
import ca.ams.services.*;

@Controller
public class InquiryController {
	@Autowired
	private UserService userService;
	@Autowired
	private InquiryService inquiryService;

	@RequestMapping(value="/api/inquiry", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> postInquiry(@RequestBody Inquiry inquiry) {
		User user = userService.getCurrentUser();
		if(user.getRole().toString().matches("ROLE_PROFESSOR|ROLE_STUDENT")) {
			inquiry.setDate(Calendar.getInstance().getTime());
			inquiry.setRole(user.getRole());
			inquiry.setUserId(user.getId());
			inquiryService.save(inquiry);
			return new ResponseEntity<String>("Success.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Only student or professor can post inquiry.", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping(value="/api/inquiry/all", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> getAllInquiries() {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_REGISTRAR) {
			List<Inquiry> inquiries = inquiryService.getAllInquiries();
			 return new ResponseEntity<List<Inquiry>>(inquiries, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Only Registrar can handle inquiries.", HttpStatus.NOT_ACCEPTABLE);
	}
}
