package ca.sms.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.sms.models.Course;
import ca.sms.models.CourseSection;
import ca.sms.models.Student;
import ca.sms.models.StudentRepository;
import ca.sms.models.User;

@Component
public class StudentService {
	@Autowired
	private StudentRepository studentRepos;
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	private CourseSection conflictedSection;
	private final static String studentIdRegex = "[0-9]+";
	private final static String studentNameRegex = "[A-Za-z\\s]";
	
	public void registerSection(Student student, CourseSection section) {
		student.getRegisteredSections().add(section);
		section.getEnrolledStudentsId().add(student.getId());
		save(student);
		courseService.save(section);
	}

	public Student create(String firstName, String lastName, int studentId, String email) {
		Student student = new Student();
		student.setEmail(email);
		student.setStudentId(studentId);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student = studentRepos.save(student);
		return student;
	}
	
	public void save(Student student) {
		studentRepos.save(student);
	}

	public boolean ifSectionsConflict(Student student, CourseSection section) {		
		Iterator<CourseSection> iterator = student.getRegisteredSections().iterator();
		while(iterator.hasNext()) {
			CourseSection registeredSection= iterator.next();
			if(registeredSection.getWeekday().equals(section.getWeekday()) && 
					registeredSection.getTimeslot().equals(section.getTimeslot())) {
				return true;
			}
		}
		return false;
	}

	public boolean ifCourseAlreadyRegistered(Student student, CourseSection section) {
		String courseId = section.getCourseId();
		Iterator<CourseSection> iterator = student.getRegisteredSections().iterator();
		while(iterator.hasNext()) {
			CourseSection registeredSection = iterator.next();
			if(registeredSection.getCourseId().equals(courseId)) {
				conflictedSection = registeredSection;
				return true;
			}
		}
		return false;
	}

	public boolean ifCourseAlreadyCompleted(Student student, CourseSection section) {
		Course course = courseService.getCourseById(section.getCourseId());
		return student.getCompletedCourseId().containsKey(course.getCourseId());
	}

	public boolean ifPrerequsitesFulfilled(Student student,	CourseSection section) {
		Course course = courseService.getCourseById(section.getCourseId());
		Set<String> completedCourseIds = student.getCompletedCourseId().keySet();
		return completedCourseIds.containsAll(course.getPrerequisiteCourseIds());
	}

	public boolean changeSection(Student student, CourseSection section) {
		if(conflictedSection.getCourseId().equals(section.getCourseId()) && 
				section.getSize() != section.getEnrolledStudentsId().size()) {
			registerSection(student, section);
			dropSection(student, conflictedSection);
			return true;
		}
		return false;
	}

	public void dropSection(Student student, CourseSection section) {
		student.getRegisteredSections().remove(section);
		save(student);
	}

	public List<Student> getStudentsById(String studentId) {
		if(studentId.matches(getStudentIdRegex())) {
			return studentRepos.findByStudentIdRegex(Integer.parseInt(studentId));
		}
		return null;
	}

	public static String getStudentIdRegex() {
		return studentIdRegex;
	}

	public List<Student> getStudentsByName(String studentName) {
		if(studentName.matches(getStudentNameRegex())) {
			List<User> users = userService.getUser(studentName, studentName, "ROLE_STUDENT");
			if(users != null) {
				Iterator<User> iterator = users.iterator();
				List<Student> students = new ArrayList<Student>();
				while(iterator.hasNext()) {
					students.add((Student) iterator.next().getDetailedUser());
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
}
