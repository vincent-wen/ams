package ca.ams.controllers;

import java.util.ArrayList;
import java.util.HashMap;
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
		List<Course> courses = courseService.getCoursesById(courseId);
		courseService.makeFulldressedCourses(courses, userService.getCurrentUser().getRole() == Role.ROLE_STUDENT);
		return courses;
	}
	
	@RequestMapping(value="/api/course/search-by-name", method = RequestMethod.POST)
	public @ResponseBody List<Course> searchByName(@RequestBody String courseName) {
		if(courseName.isEmpty()) return null;
		List<Course> courses = courseService.getCoursesByName(courseName);
		courseService.makeFulldressedCourses(courses, userService.getCurrentUser().getRole() == Role.ROLE_STUDENT);
		return courses;
	}
	
	@RequestMapping(value="/api/course/search-all", method = RequestMethod.POST)
	public @ResponseBody List<Course> searchAll() {
		List<Course> courses = courseService.getAllCourses();
		courseService.makeFulldressedCourses(courses, userService.getCurrentUser().getRole() == Role.ROLE_STUDENT);
		return courses;
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
				return new ResponseEntity<String>("Error: Section not found.", HttpStatus.NOT_FOUND);
			if(newTimeslot == null)
				return new ResponseEntity<String>("Error: The timeslot is not found.", HttpStatus.NOT_ACCEPTABLE);
			if(weekday == null)
				return new ResponseEntity<String>("Error: The weekday is not valid.", HttpStatus.NOT_ACCEPTABLE);
			if(section.getTimeslot().equals(newTimeslot) && section.getWeekday().equals(weekday))
				return new ResponseEntity<String>("Error: The time is already set.", HttpStatus.NOT_ACCEPTABLE);
			
			Professor professor = professorService.getProfessorById(section.getInstructorId());
			if(professorService.isTimeConflictForProfessor(professor, newTimeslot, weekday))
				return new ResponseEntity<String>("Error: The time is conflit to another course of the same instructor.", HttpStatus.NOT_ACCEPTABLE);
			
			// Process modifications
			section.setTimeslot(newTimeslot);
			section.setWeekday(weekday);
			courseService.save(section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error: Forbidden Request.", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/api/section/change-instructor", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changeInstructor(@RequestBody CourseSection courseSection) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_GPD) {
			// find objects
			CourseSection section = courseService.getSectionById(courseSection.getId());
			Professor newInstructor = professorService.getProfessorByName(courseSection.getInstructor().getName());
			
			// Handle exceptions before modifications
			if(section == null)
				return new ResponseEntity<String>("Error: Section not found.", HttpStatus.NOT_FOUND);
			if(newInstructor == null)
				return new ResponseEntity<String>("Error: Professor not found.", HttpStatus.NOT_FOUND);
			if(professorService.ifSectionAlreadyRegistered(newInstructor, section))
				return new ResponseEntity<String>("Error: The professor have already been registered in this course section.", HttpStatus.NOT_ACCEPTABLE);
			if(professorService.ifSectionsConflict(newInstructor, section))
				return new ResponseEntity<String>("Error: Time is conflict with another course section for this professor.", HttpStatus.CONFLICT);
			
			// Process modifications
			Professor oldInstructor = professorService.getProfessorById(section.getInstructorId());
			section.setInstructorId(newInstructor.getId());
			newInstructor.getInstructedSections().add(section);
			oldInstructor.getInstructedSections().remove(section);
			professorService.save(oldInstructor);
			professorService.save(newInstructor);
			courseService.save(section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error: Forbidden Request.", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/api/section/change-location", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changeLocation(@RequestBody CourseSection courseSection) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_GPD) {
			// find objects
			CourseSection section = courseService.getSectionById(courseSection.getId());

			// Handle exceptions before modifications
			if(section == null)
				return new ResponseEntity<String>("Error: Course section not found.", HttpStatus.NOT_FOUND);
			if(!courseService.validateLocation(courseSection.getLocation()))
				return new ResponseEntity<String>("Error: The format of location is invalid.", HttpStatus.NOT_ACCEPTABLE);

			// Process modifications
			section.setLocation(courseSection.getLocation());
			courseService.save(section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error: Forbidden Request.", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/api/section/change-capacity", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changeCapacity(@RequestBody CourseSection courseSection) {
		User user = userService.getCurrentUser();
		if(user.getRole() == Role.ROLE_GPD) {
			// find objects
			CourseSection section = courseService.getSectionById(courseSection.getId());

			// Handle exceptions before modifications
			if(section == null)
				return new ResponseEntity<String>("Error: Course section not found.", HttpStatus.NOT_FOUND);
			if(!courseService.validateCapacity(courseSection.getCapacity()))
				return new ResponseEntity<String>("Error: Capacity must be an integer between 10 to 300.", HttpStatus.NOT_ACCEPTABLE);
			if(courseService.ifEnrolledStudentsMoreThanCapacity(section, courseSection.getCapacity()))
				return new ResponseEntity<String>("Error: Capacity must be bigger than the number of students who have been enrolled in this course section.", HttpStatus.NOT_ACCEPTABLE);

			// Process modifications
			section.setCapacity(courseSection.getCapacity());
			courseService.save(section);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error: Forbidden Request.", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/api/section/get-enrolled-students", method = RequestMethod.POST)
	public @ResponseBody List<Student> getEnrolledStudentsForSection(@RequestBody String sectionId) {
		User user = userService.getCurrentUser();
		if(user.getRole().toString().matches("ROLE_PROFESSOR|ROLE_REGISTRAR|ROLE_GPD")) {
			CourseSection section = courseService.getSectionById(sectionId);
			List<Student> enrolledStudents = new ArrayList<Student>();
			for(String studentId : section.getEnrolledStudentsId()) {
				Student student = studentService.getStudentById(studentId);
				student.setPassword(null);
				enrolledStudents.add(student);
			}
			return enrolledStudents;
		}
		return null;
	}
	
	@RequestMapping(value="/api/course/get-grading-system", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> getGradingSystem() {
		HashMap<String, String> gradingSystem = new HashMap<String, String>();
		for(Grade grade : Grade.values()) {
			gradingSystem.put(grade.toString(), grade.grade());
		}
		return gradingSystem;
	}
	
	@RequestMapping(value="/api/course/get-all-timeslots", method = RequestMethod.POST)
	public @ResponseBody List<Timeslot> getAllTimeslots() {
		return courseService.getAllTimeslots();
	}
}
