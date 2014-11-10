package ca.ams.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.*;

@Component
public class StudentService {
	@Autowired
	private StudentRepository studentRepos;
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;

	private final static String studentIdRegex = "[0-9]+";
	private final static String studentNameRegex = "[A-Za-z\\s]+";
	
	public void registerSection(Student student, CourseSection section) {
		student.getRegisteredSections().add(section);
		section.getEnrolledStudentsId().add(student.getId());
		save(student);
		courseService.save(section);
	}
	
	public void save(Student student) {
		studentRepos.save(student);
	}

	public boolean ifSectionsConflict(Student student, CourseSection section, boolean ignoreSameCourse) {		
		Iterator<CourseSection> iterator = student.getRegisteredSections().iterator();
		while(iterator.hasNext()) {
			CourseSection registeredSection= iterator.next();
			if(registeredSection.getWeekday().equals(section.getWeekday()) && 
					registeredSection.getTimeslot().equals(section.getTimeslot())) {
				if(ignoreSameCourse && registeredSection.getCourseObjectId().equals(section.getCourseObjectId())) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public boolean ifCourseAlreadyRegistered(Student student, CourseSection section) {
		String courseId = section.getCourseObjectId();
		Iterator<CourseSection> iterator = student.getRegisteredSections().iterator();
		while(iterator.hasNext()) {
			CourseSection registeredSection = iterator.next();
			if(registeredSection.getCourseObjectId().equals(courseId)) {
				return true;
			}
		}
		return false;
	}

	public boolean ifCourseAlreadyCompleted(Student student, CourseSection section) {
		Course course = courseService.getCourseById(section.getCourseObjectId());
		return student.getCompletedCourseId().containsKey(course.getCourseId());
	}

	public boolean ifPrerequsitesFulfilled(Student student,	CourseSection section) {
		Course course = courseService.getCourseById(section.getCourseObjectId());
		Set<String> completedCourseIds = student.getCompletedCourseId().keySet();
		return completedCourseIds.containsAll(course.getPrerequisiteCourseIds());
	}

	public void changeSection(Student student, CourseSection section) {
		Iterator<CourseSection> registeredSections = student.getRegisteredSections().iterator();
		String courseObjectId = section.getCourseObjectId();
		while(registeredSections.hasNext()) {
			CourseSection registeredSection = registeredSections.next();
			if(registeredSection.getCourseObjectId().equals(courseObjectId)) {
				dropSection(student, registeredSection);
				registerSection(student, section);
				return;
			}
		}
	}

	public void dropSection(Student student, CourseSection section) {
		student.getRegisteredSections().remove(section);
		save(student);
	}

	public List<Student> getStudentsById(String studentId) {
		if(studentId.matches(getStudentIdRegex())) {
			return studentRepos.findByStudentId(Integer.parseInt(studentId));
		}
		return null;
	}

	public static String getStudentIdRegex() {
		return studentIdRegex;
	}

	public List<Student> getStudentsByName(String studentName) {
		if(studentName.matches(getStudentNameRegex())) {
			List<Student> students = studentRepos.findByNameRegex(studentName);
			if(students != null) {
				Iterator<Student> iterator = students.iterator();
				while(iterator.hasNext()) {
					iterator.next().setPassword(null);
				}
				return students;
			}
		}
		return null;
	}

	public List<Student> getAllStudents() {
		return studentRepos.findAll();
	}

	public static String getStudentNameRegex() {
		return studentNameRegex;
	}
	
	public Student getStudentById(String id) {
		return studentRepos.findOne(id);
	}

	public boolean ifSectionAlreadyRegistered(Student student, CourseSection section) {
		return student.getRegisteredSections().contains(section);
	}
}
