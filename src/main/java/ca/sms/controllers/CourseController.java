package ca.sms.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.sms.models.*;
import ca.sms.services.*;

@Controller
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
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
	public @ResponseBody List<Course> searchAll() {
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
	
	@RequestMapping(value = "/api/section/get-enrolled-students", method = RequestMethod.POST)
	public @ResponseBody List<Student> getEnrolledStudentsForSection(@RequestBody String sectionId) {
		User user = userService.getCurrentUser();
		if(user.getRole().matches("ROLE_PROFESSOR|ROLE_REGISTRAR|ROLE_GPD")) {
			CourseSection section = courseService.getSectionById(sectionId);
			List<Student> enrolledStudents = new ArrayList<Student>();
			List<String> studentIds = section.getEnrolledStudentsId();
			Iterator<String> iterator = studentIds.iterator();
			while(iterator.hasNext()) {
				enrolledStudents.add(studentService.getStudentById(iterator.next()));
			}
			return enrolledStudents;
		}
		return null;
	}
}
