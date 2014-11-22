package ca.ams.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.*;

@Component
public class InquiryService {
	@Autowired
	private InquiryRepository inquiryRepos;
	
	public void save(Inquiry inquiry) {
		inquiryRepos.save(inquiry);
	}

	public List<Inquiry> getAllInquiries() {
		return inquiryRepos.findAll();
	}
}
