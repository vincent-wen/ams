package ca.sms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.sms.models.Professor;
import ca.sms.models.ProfessorRepository;
import ca.sms.models.User;

@Component
public class ProfessorService {
	@Autowired
	private ProfessorRepository professorRepos;
	@Autowired
	private UserService userService;
	
	public Professor create(String name) {
		Professor professor = new Professor();
		professor.setUsername(name);
		return save(professor);
	}

	public Professor save(Professor professor) {
		return professorRepos.save(professor);
	}
	
	public Professor getProfessorByName(String name) {
		String[] names = name.trim().split(" ");
		User user = userService.getUser(names[0], names[1]);
		if(user == null) return null;
		return (Professor) user.getDetailedUser();
	}
}
