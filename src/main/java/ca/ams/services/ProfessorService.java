package ca.ams.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.Professor;
import ca.ams.models.ProfessorRepository;

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
	
	public List<Professor> getProfessorByName(String name) {
		List<Professor> professors = getProfessor(name);
		Iterator<Professor> iterator = professors.iterator();
		while(iterator.hasNext()) {
			iterator.next().setPassword(null);
		}
		return professors;
	}

	public List<Professor> getProfessor(String name) {
		return professorRepos.findByNameRegex(name);
	}

	public Professor getProfessorById(String instructorId) {
		return instructorId == null ? null:professorRepos.findOne(instructorId);
	}

	public List<Professor> getAllProfessors() {
		return professorRepos.findAll();
	}
}
