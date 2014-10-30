package ca.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.sms.models.Course;
import ca.sms.models.CourseSection;
import ca.sms.models.Professor;
import ca.sms.models.ProfessorRepository;
import ca.sms.models.Student;
import ca.sms.models.Timeslot;
import ca.sms.models.User;
import ca.sms.services.CourseService;
import ca.sms.services.ProfessorService;
import ca.sms.services.UserService;

@Controller
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProfessorService professorService;
	
	@RequestMapping(value="/api/course/search-by-id", method = RequestMethod.POST)
	public @ResponseBody List<Course> searchById(@RequestBody String courseId) {
		if(courseId.isEmpty()) return null;
		return courseService.getCoursesById(courseId);
	}
	
	@RequestMapping(value="/api/course/search-by-name", method = RequestMethod.POST)
	public @ResponseBody List<Course> searchByName(@RequestBody String courseName) {
		if(courseName.isEmpty()) return null;
		return courseService.getCoursesByName(courseName);
	}
	
	@RequestMapping(value="/api/course/search-all", method = RequestMethod.POST)
	public @ResponseBody List<Course> searchById() {
		return courseService.getAllCourses();
	}
	
	@RequestMapping(value="/api/course/change-section", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changeSectionCapacity(@RequestBody String sectionId, 
			@RequestBody int newCapacity, @RequestBody String newStartTime, @RequestBody String newInstructorsName) {
		User user = userService.getCurrentUser();
		if(user.getRole().equals("ROLE_REGISTRAR")) {
			// find objects
			CourseSection section = courseService.getSectionById(sectionId);
			Timeslot newTimeslot = courseService.getTimeslotByStartTime(newStartTime);
			Professor newInstructor = professorService.getProfessorByName(newInstructorsName);
			
			// Handle exceptions before modifications
			if(section == null)
				return new ResponseEntity<String>("Section not found.", HttpStatus.NOT_FOUND);
			if(section.getSize() > newCapacity) 
				return new ResponseEntity<String>("It only allows to expand section's capacity.", HttpStatus.NOT_ACCEPTABLE);
			if(newTimeslot == null)
				return new ResponseEntity<String>("New timeslot is not valid.", HttpStatus.NOT_ACCEPTABLE);
			if(newInstructor == null)
				return new ResponseEntity<String>("Professor not found.", HttpStatus.NOT_FOUND);
			
			// Process modifications
			section.setSize(newCapacity);
			section.setTimeslot(newTimeslot);
			section.setInstructor(newInstructor);
			courseService.save(section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Forbidden Request.", HttpStatus.FORBIDDEN);
	}

}
