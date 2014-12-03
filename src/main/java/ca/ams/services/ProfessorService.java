package ca.ams.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.*;

@Component
public class ProfessorService {
	@Autowired
	private ProfessorRepository professorRepos;
	@Autowired
	private UserService userService;
	private final static String nameRegex = "[A-Za-z\\s\\.]+";
	
	public Professor create(String name) {
		Professor professor = new Professor();
		professor.setUsername(name);
		return save(professor);
	}

	public Professor save(Professor professor) {
		return professorRepos.save(professor);
	}
	
	public List<Professor> getProfessorsByName(String name) {
		if(name == null || !name.matches(nameRegex)) return new ArrayList<Professor>();
		List<Professor> professors = professorRepos.findByNameRegex(name);
		return professors;
	}
	
	public Professor getProfessorById(String instructorId) {
		return instructorId == null ? null:professorRepos.findOne(instructorId);
	}

	public List<Professor> getAllProfessors() {
		return professorRepos.findAll();
	}

	public boolean ifSectionAlreadyRegistered(Professor professor, CourseSection section) {
		List<CourseSection> sections = professor.getInstructedSections();
		return sections.contains(section);
	}
	
	public void clearSensitiveInfo(Professor professor) {
		professor.setId(null);
		professor.setPassword(null);
		professor.setUsername(null);
		professor.getInstructedSections().clear();
	}

	public boolean ifSectionConflicts(Professor professor, CourseSection section) {
		for(CourseSection registeredSection : professor.getInstructedSections()) {
			if(!registeredSection.equals(section) && 
					registeredSection.getSchedule().ifConflicts(section.getSchedule())) {
				return true;
			}
		}
		return false;
	}
}
