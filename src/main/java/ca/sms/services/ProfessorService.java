package ca.sms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.sms.models.Professor;
import ca.sms.models.ProfessorRepository;

@Component
public class ProfessorService {
	@Autowired
	private ProfessorRepository repos;
	
	public Professor create(String name) {
		Professor professor = new Professor();
		professor.setUsername(name);
		return repos.save(professor);
	}
}
