package ca.ams.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.*;

@Component
public class InquiryService {
	@Autowired
	private InquiryRepository inquiryRepos;
	
	private static final String titleRegex = "^[\\w\\.\\-\\_\\s]{1,50}$";
	private static final String contentRegex = "^.{1,1000}$";
	
	public void save(Inquiry inquiry) {
		inquiryRepos.save(inquiry);
	}

	public List<Inquiry> getAllInquiries() {
		return inquiryRepos.findAll();
	}

	public boolean validateLength(Inquiry inquiry) {
		return inquiry.getContent().matches(contentRegex) && inquiry.getTitle().matches(titleRegex);
	}
}
