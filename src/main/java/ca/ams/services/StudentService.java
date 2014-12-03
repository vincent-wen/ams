package ca.ams.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	@Autowired
	private ProfessorService professorService;

	private final static String studentIdRegex = "[0-9]+";
	private final static String studentNameRegex = "[A-Za-z\\s\\.]+";

	public void registerSection(Student student, CourseSection section) {
		student.getRegisteredSections().add(section);
		section.getEnrolledStudentsId().add(student.getId());
		save(student);
		courseService.save(section);
	}

	public void save(Student student) {
		studentRepos.save(student);
	}

	public boolean ifSectionsConflict(Student student, CourseSection section,
			boolean ignoreSameCourse) {
		for (CourseSection registeredSection : student.getRegisteredSections()) {
			if (registeredSection.getSchedule().ifConflicts(section.getSchedule())) {
				if (ignoreSameCourse && registeredSection.getCourseObjectId().equals(section.getCourseObjectId())) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public boolean ifCourseAlreadyRegistered(Student student, CourseSection section) {
		String courseId = section.getCourseObjectId();
		for (CourseSection registeredSection : student.getRegisteredSections()) {
			if (registeredSection.getCourseObjectId().equals(courseId)) {
				return true;
			}
		}
		return false;
	}

	public boolean ifCourseAlreadyCompleted(Student student, CourseSection section) {
		for(Course course : student.getCompletedCourses()) {
			if(course.getId().equals(section.getCourseObjectId())) {
				return true;
			}
		}
		return false;
	}

	public List<String> getPrerequisitesNotFulfilled(Student student, CourseSection section) {
		Course course = courseService.getCourseById(section.getCourseObjectId());
		List<String> completedCourseIds = new ArrayList<String>(); 
		for(Course completedCourse : student.getCompletedCourses()) {
			completedCourseIds.add(completedCourse.getId());
		}
		List<String> prerequisitesNotFulfilled = new ArrayList<String>();
		for (String courseObjectId : course.getPrerequisiteCourseIds()) {
			if (completedCourseIds.contains(courseObjectId))
				continue;
			prerequisitesNotFulfilled.add(courseService.getCourseById(courseObjectId).getCourseId());
		}
		return prerequisitesNotFulfilled;
	}

	public void changeSection(Student student, CourseSection section) {
		String courseObjectId = section.getCourseObjectId();
		for (CourseSection registeredSection : student.getRegisteredSections()) {
			if (registeredSection.getCourseObjectId().equals(courseObjectId)) {
				dropSection(student, registeredSection);
				registerSection(student, section);
				return;
			}
		}
	}

	public void dropSection(Student student, CourseSection section) {
		if (student == null || section == null)	return;
		student.getRegisteredSections().remove(section);
		section.getEnrolledStudentsId().remove(student.getId());
		save(student);
		courseService.save(section);
	}

	public List<Student> getStudentsById(String studentId) {
		if (studentId == null || !studentId.matches(getStudentIdRegex()))
			return new ArrayList<Student>();
		List<Student> students = studentRepos.findByStudentIdRegex(studentId);
		clearPassword(students);
		return students;
	}

	public static String getStudentIdRegex() {
		return studentIdRegex;
	}

	public void clearPassword(List<Student> students) {
		if (students == null)
			return;
		for (Student student : students) {
			student.setPassword(null);
		}
	}

	public List<Student> getStudentsByName(String studentName) {
		if (studentName == null || !studentName.matches(getStudentNameRegex())) 
			return new ArrayList<Student>();
		List<Student> students = studentRepos.findByNameRegex(studentName);
		clearPassword(students);
		return students;
	}

	public List<Student> getAllStudents() {
		return studentRepos.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	public static String getStudentNameRegex() {
		return studentNameRegex;
	}

	public Student getStudentById(String id) {
		return id == null ? null : studentRepos.findOne(id);
	}

	public boolean ifSectionAlreadyRegistered(Student student, CourseSection section) {
		List<CourseSection> registeredSections = student.getRegisteredSections();
		if (registeredSections.isEmpty()) {
			if (section == null)
				return true;
			return false;
		}
		return student.getRegisteredSections().contains(section);
	}

	public void makeFulldressedStudent(Student student) {
		if (student == null) return;
		for (CourseSection section : student.getRegisteredSections()) {
			String id = section.getCourseObjectId();
			section.setCourseId(courseService.getCourseById(id).getCourseId());
			Professor instructor = professorService.getProfessorById(section.getInstructorId());
			professorService.clearSensitiveInfo(instructor);
			section.setInstructor(instructor);
		}
	}

	public void getEnrolledStudent(Student student, String courseObjectId) {
		student.setPassword(null);
		Iterator<Course> courses = student.getCompletedCourses().iterator();
		while(courses.hasNext()) {
			if(courses.next().getId().equals(courseObjectId)) {
				continue;
			}
			courses.remove();
		}
		student.setAlreadyPaid(null);
		student.setPenalty(null);
		student.setTuition(null);
		student.setUsername(null);
		student.getRegisteredSections().clear();
	}

	public void saveGrades(Student simpleStudent) {
		Student student = getStudentById(simpleStudent.getId());
		for(Course simpleCourse : simpleStudent.getCompletedCourses()) {
			for(Course completedCourse : student.getCompletedCourses()) {
				if(completedCourse.getId().equals(simpleCourse.getId())) {
					completedCourse.setGrade(simpleCourse.getGrade());
					completedCourse.setYear(simpleCourse.getYear());
					completedCourse.setTerm(simpleCourse.getTerm());
					save(student);
					return;
				}
			}
			Course course = courseService.getCourseById(simpleCourse.getId());
			course.setCourseCompleted(simpleCourse.getGrade(), simpleCourse.getYear(), simpleCourse.getTerm());
			student.getCompletedCourses().add(course);			
		}
		save(student);
	}
}
