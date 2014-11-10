package ca.ams.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.ams.models.CourseSection;
import ca.ams.models.Role;
import ca.ams.models.Student;
import ca.ams.models.User;
import ca.ams.services.CourseService;
import ca.ams.services.StudentService;
import ca.ams.services.UserService;

@Controller
public class StudentController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value="/api/student/search-by-id", method = RequestMethod.POST)
	public @ResponseBody List<Student> searchById(@RequestBody String studentId) {
		Role role = userService.getCurrentUser().getRole();
		if(role.toString().matches("ROLE_GPD|ROLE_PROFESSOR|ROLE_REGISTRAR")) {
			return studentService.getStudentsById(studentId);
		}
		return null;
	}
	
	@RequestMapping(value="/api/student/search-by-name", method = RequestMethod.POST)
	public @ResponseBody List<Student> searchByName(@RequestBody String studentName) {
		Role role = userService.getCurrentUser().getRole();
		if(role.toString().matches("ROLE_GPD|ROLE_PROFESSOR|ROLE_REGISTRAR")) {
			return studentService.getStudentsByName(studentName);
		}
		return null;
	}
	
	@RequestMapping(value="/api/student/search-all", method = RequestMethod.POST)
	public @ResponseBody List<Student> searchAll() {
		Role role = userService.getCurrentUser().getRole();
		if(role.toString().matches("ROLE_GPD|ROLE_PROFESSOR|ROLE_REGISTRAR")) {
			return studentService.getAllStudents();
		}
		return null;
	}
	
	@RequestMapping(value="/api/student/register-course", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> registerSection(@RequestBody String sectionId) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_STUDENT) {
			CourseSection section = courseService.getSectionById(sectionId);
			Student student = (Student) user;
			if(studentService.ifSectionsConflict(student, section, false))
				return new ResponseEntity<String>("Time is conflict with another course.", HttpStatus.CONFLICT);
			if(studentService.ifCourseAlreadyRegistered(student, section))
				return new ResponseEntity<String>("You can not register for the same course twice within the same semester.", HttpStatus.CONFLICT);
			if(courseService.isSectionFull(section))
				return new ResponseEntity<String>("This course section is full. Please choose another one.", HttpStatus.NOT_ACCEPTABLE);
			if(studentService.ifCourseAlreadyCompleted(student, section))
				return new ResponseEntity<String>("This course has already been completed.", HttpStatus.NOT_ACCEPTABLE);
			if(!studentService.ifPrerequsitesFulfilled(student, section))
				return new ResponseEntity<String>("You haven't fulfill the prerequisites of the course.", HttpStatus.NOT_ACCEPTABLE);

			studentService.registerSection(student, section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("failed", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/api/student/drop-course", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> dropCourse(@RequestBody String sectionId) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_STUDENT) {
			Student student = (Student) user;
			CourseSection section = courseService.getSectionById(sectionId);
			studentService.dropSection(student, section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Forbidden request.", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping(value="/api/student/change-section", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changeSection(@RequestBody String sectionId) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_STUDENT) {
			CourseSection section = courseService.getSectionById(sectionId);
			Student student = (Student) user;
			if(studentService.ifSectionAlreadyRegistered(student, section))
				return new ResponseEntity<String>("You have already registered this course section.", HttpStatus.NOT_ACCEPTABLE);
			if(studentService.ifSectionsConflict(student, section, true))
				return new ResponseEntity<String>("Time is conflict with another course.", HttpStatus.CONFLICT);
			if(courseService.isSectionFull(section))
				return new ResponseEntity<String>("This course section is full. Please choose another one.", HttpStatus.NOT_ACCEPTABLE);
			if(!studentService.ifCourseAlreadyRegistered(student, section))
				return new ResponseEntity<String>("You have not registered this course.", HttpStatus.NOT_ACCEPTABLE);
			studentService.changeSection(student, section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Forbidden request.", HttpStatus.NOT_ACCEPTABLE);
	}
}
