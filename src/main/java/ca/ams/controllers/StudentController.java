package ca.ams.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.ams.models.*;
import ca.ams.services.*;

@Controller
public class StudentController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ProfessorService professorService;
	
	@RequestMapping(value="/api/student/search-by-id", method = RequestMethod.POST)
	public @ResponseBody List<Student> searchById(@RequestBody String studentId) {
		Role role = userService.getCurrentUser().getRole();
		if(role.toString().matches("ROLE_GPD|ROLE_PROFESSOR|ROLE_REGISTRAR")) {
			List<Student> students = studentService.getStudentsById(studentId);
			if(students == null) return null;
			for(Student student : students) {
				student.setPassword(null);
			}
			return students;
		}
		return null;
	}
	
	@RequestMapping(value="/api/student/search-by-name", method = RequestMethod.POST)
	public @ResponseBody List<Student> searchByName(@RequestBody String studentName) {
		Role role = userService.getCurrentUser().getRole();
		if(role.toString().matches("ROLE_GPD|ROLE_PROFESSOR|ROLE_REGISTRAR")) {
			List<Student> students = studentService.getStudentsByName(studentName);
			if(students == null) return null;
			for(Student student : students) {
				student.setPassword(null);
			}
			return students;
		}
		return null;
	}
	
	@RequestMapping(value="/api/student/search-all", method = RequestMethod.POST)
	public @ResponseBody List<Student> searchAll() {
		Role role = userService.getCurrentUser().getRole();
		if(role.toString().matches("ROLE_GPD|ROLE_PROFESSOR|ROLE_REGISTRAR")) {
			List<Student> students = studentService.getAllStudents();
			for(Student student : students) {
				student.setPassword(null);
			}
			return students;
		}
		return null;
	}
	
	@RequestMapping(value="/api/student/register-course", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> registerSection(@RequestBody String sectionId) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_STUDENT) {
			CourseSection section = courseService.getSectionById(sectionId);
			Student student = (Student) user;
			
			if(studentService.ifSectionAlreadyRegistered(student, section))
				return new ResponseEntity<String>("You have already registered this course section.", HttpStatus.NOT_ACCEPTABLE);
			if(studentService.ifCourseAlreadyCompleted(student, section))
				return new ResponseEntity<String>("This course has already been completed.", HttpStatus.NOT_ACCEPTABLE);
			if(!studentService.ifPrerequsitesFulfilled(student, section))
				return new ResponseEntity<String>("You haven't fulfilled the prerequisites of the course.", HttpStatus.NOT_ACCEPTABLE);
			if(studentService.ifCourseAlreadyRegistered(student, section))
				return new ResponseEntity<String>("You can not register for the same course twice within the same semester.", HttpStatus.CONFLICT);
			if(studentService.ifSectionsConflict(student, section, false))
				return new ResponseEntity<String>("Time is conflict with another course.", HttpStatus.CONFLICT);
			if(courseService.isSectionFull(section))
				return new ResponseEntity<String>("This course section is full. Please choose another one.", HttpStatus.NOT_ACCEPTABLE);
			
			studentService.registerSection(student, section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Illegal Operation", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping(value="/api/student/register-course/{studentId}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> registerSectionForStudent(@RequestBody String sectionId, @PathVariable String studentId) {
		User user = userService.getCurrentUser();
		if(user.getRole().toString().matches("ROLE_REGISTRAR|ROLE_GPD")) {
			CourseSection section = courseService.getSectionById(sectionId);
			Student student = studentService.getStudentById(studentId);
			
			if(studentService.ifSectionAlreadyRegistered(student, section))
				return new ResponseEntity<String>("The Student has been registered in this course section.", HttpStatus.NOT_ACCEPTABLE);
			if(studentService.ifCourseAlreadyRegistered(student, section))
				return new ResponseEntity<String>("The Student has been registered in another section of the same course.", HttpStatus.CONFLICT);
			
			studentService.registerSection(student, section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Illegal Operation", HttpStatus.NOT_ACCEPTABLE);
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
	
	@RequestMapping(value="/api/student/drop-course/{studentId}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> dropCourseForStudent(@RequestBody String sectionId, @PathVariable String studentId) {
		User user = userService.getCurrentUser();
		if(user.getRole().toString().matches("ROLE_REGISTRAR|ROLE_GPD")) {
			CourseSection section = courseService.getSectionById(sectionId);
			Student student = studentService.getStudentById(studentId);
			
			studentService.dropSection(student, section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Illegal Operation", HttpStatus.NOT_ACCEPTABLE);
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
	
	@RequestMapping(value="/api/student/get-detailed-student", method = RequestMethod.POST)
	public @ResponseBody Student getDetailedStudent(@RequestBody String studentId) {
		User user = userService.getCurrentUser();
		if(user.getRole().toString().matches("ROLE_GPD|ROLE_REGISTRAR")) {
			Student student = studentService.getStudentById(studentId);
			if(student == null) return null;
			
			student.setPassword(null);
			studentService.makeFulldressedStudent(student);
			return student;
		}
		return null;
	}
}
