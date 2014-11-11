package ca.ams.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.CourseSection;
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
	
	public List<Professor> getProfessorsByName(String name) {
		List<Professor> professors = professorRepos.findByNameRegex(name);
		for(Professor professor : professors) {
			professor.setPassword(null);
		}
		return professors;
	}
	
	public Professor getProfessorById(String instructorId) {
		return instructorId == null ? null:professorRepos.findOne(instructorId);
	}
	
	public Professor getProfessorByName(String instructorName) {
		return professorRepos.findByNameRegex(instructorName).get(0);
	}

	public List<Professor> getAllProfessors() {
		return professorRepos.findAll();
	}

	public boolean ifSectionsConflict(Professor professor, CourseSection section) {
		for(CourseSection registeredSection : professor.getInstructedSections()) {
			if(registeredSection.getWeekday().equals(section.getWeekday()) && 
					registeredSection.getTimeslot().equals(section.getTimeslot())) {
				return true;
			}
		}
		return false;
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
}
