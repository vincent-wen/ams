package ca.sms.services;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.sms.models.Course;
import ca.sms.models.CourseSection;
import ca.sms.models.Student;
import ca.sms.models.StudentRepository;

@Component
public class StudentService {
	@Autowired
	private StudentRepository studentRepos;
	@Autowired
	private CourseService courseService;
	private CourseSection conflictedSection;
	
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
}
