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
	@Autowired
	private StudentService studentService;
	@Autowired
	private ProfessorService professorService;

	@RequestMapping(value="/api/inquiry", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> postInquiry(@RequestBody Inquiry inquiry) {
		User user = userService.getCurrentUser();
		if(user.getRole().toString().matches("ROLE_PROFESSOR|ROLE_STUDENT")) {
			if(!inquiryService.validateLength(inquiry))
				return new ResponseEntity<String>("Error: Title or content's length is beyond the maximum.", HttpStatus.NOT_ACCEPTABLE);
			inquiry.setDate(Calendar.getInstance().getTime());
			inquiry.setRole(user.getRole());
			inquiry.setAuthorId(user.getId());
			inquiryService.save(inquiry);
			return new ResponseEntity<String>("Inquiry has been sent successfully. Registrar will reply to your email. Thank you.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error: Only student or professor can post inquiry.", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping(value="/api/inquiry/all", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> getAllInquiries() {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_REGISTRAR) {
			List<Inquiry> inquiries = inquiryService.getAllInquiries();
			for(Inquiry inquiry : inquiries) {
				Role role = inquiry.getRole();
				User author = null;
				if(role == Role.ROLE_STUDENT) {
					author = studentService.getStudentById(inquiry.getAuthorId());
				} else if(role == Role.ROLE_PROFESSOR) {
					author = professorService.getProfessorById(inquiry.getAuthorId());
				}
				inquiry.setAuthor(author.getName());
				if(inquiry.getEmail() == null || inquiry.getEmail().isEmpty())
					inquiry.setEmail(author.getEmail());
				inquiry.setPhoneNumber(author.getPhoneNumber());
			}
			return new ResponseEntity<List<Inquiry>>(inquiries, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error: Only Registrar can handle inquiries.", HttpStatus.NOT_ACCEPTABLE);
	}
}
