package ca.ams.controllers;

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

import ca.ams.models.*;
import ca.ams.services.*;

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
	
	@RequestMapping(value="/api/section/change-time", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changeTime(@RequestBody CourseSection courseSection) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_GPD) {
			// find objects
			CourseSection section = courseService.getSectionById(courseSection.getId());
			Timeslot newTimeslot = courseService.getTimeslotByStartTimeAndEndTime(courseSection.getTimeslot().getStartTime().trim(), courseSection.getTimeslot().getEndTime().trim());
			Weekday weekday = courseSection.getWeekday();

			// Handle exceptions before modifications
			if(section == null)
				return new ResponseEntity<String>("Section not found.", HttpStatus.NOT_FOUND);
			if(newTimeslot == null)
				return new ResponseEntity<String>("The timeslot is not found.", HttpStatus.NOT_ACCEPTABLE);
			if(weekday == null)
				return new ResponseEntity<String>("The weekday is not valid.", HttpStatus.NOT_ACCEPTABLE);
			
			// Process modifications
			section.setTimeslot(newTimeslot);
			section.setWeekday(weekday);
			courseService.save(section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Forbidden Request.", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/api/section/change-instructor", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changeInstructor(@RequestBody CourseSection courseSection) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_GPD) {
			CourseSection section = courseService.getSectionById(courseSection.getId());
			Professor newInstructor = professorService.getProfessorById(courseSection.getInstructor().getId());
			
			if(section == null)
				return new ResponseEntity<String>("Section not found.", HttpStatus.NOT_FOUND);
			if(newInstructor == null)
				return new ResponseEntity<String>("Professor not found.", HttpStatus.NOT_FOUND);
			
			section.setInstructor(courseSection.getInstructor());
			courseService.save(section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Forbidden Request.", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/api/section/get-enrolled-students", method = RequestMethod.POST)
	public @ResponseBody List<Student> getEnrolledStudentsForSection(@RequestBody String sectionId) {
		User user = userService.getCurrentUser();
		if(user.getRole().toString().matches("ROLE_PROFESSOR|ROLE_REGISTRAR|ROLE_GPD")) {
			CourseSection section = courseService.getSectionById(sectionId);
			List<Student> enrolledStudents = new ArrayList<Student>();
			List<String> studentIds = section.getEnrolledStudentsId();
			Iterator<String> iterator = studentIds.iterator();
			while(iterator.hasNext()) {
				Student student = studentService.getStudentById(iterator.next());
				student.setPassword(null);
				enrolledStudents.add(student);
			}
			return enrolledStudents;
		}
		return null;
	}
}
