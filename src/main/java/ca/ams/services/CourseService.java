package ca.ams.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.*;

@Component
public class CourseService {
	@Autowired
	private CourseSectionRepository sectionRepos;
	@Autowired
	private CourseRepository courseRepos;
	@Autowired
	private TimeslotRepository timeslotRepos;
	@Autowired
	private ProfessorService professorService;
	
	private final static String courseIdAndNameRegex = "[\\w]+";
	private final static String locationRegex = "[A-Za-z]{2}[-_]{1}[\\d]{3}";

	public List<Course> getCoursesById(String courseId) {
		if(!courseId.matches(courseIdAndNameRegex)) return null;
		String regex = ".*" + courseId + ".*";
		return courseRepos.findByCourseIdRegex(regex);
	}
	
	public List<Course> getCoursesByName(String courseName) {
		if(!courseName.matches(courseIdAndNameRegex)) return null;
		String regex = ".*" + courseName + ".*";
		return courseRepos.findByCourseNameRegex(regex);
	}
	
	public List<Course> getAllCourses() {
		return courseRepos.findAll();
	}
	
	public CourseSection getSectionById(String sectionId) {
		return sectionRepos.findOne(sectionId);
	}

	public void save(CourseSection section) {
		sectionRepos.save(section);
	}
	
	public void save(Course course) {
		courseRepos.save(course);
	}

	public Course getCourseById(String Id) {
		return courseRepos.findOne(Id);
	}

	public boolean isSectionFull(CourseSection section) {
		return section.getEnrolledStudentsId().size() == section.getCapacity();
	}

	public Timeslot getTimeslotByStartTimeAndEndTime(String startTime, String endTime) {
		return timeslotRepos.findByStartTimeAndEndTime(startTime, endTime);
	}

	public void addInstructorsInfo(List<Course> courses) {
		if(courses == null) return;
		for(Course course : courses) {
			for(CourseSection section : course.getCourseSections()) {
				Professor instructor = professorService.getProfessorById(section.getInstructorId());
				professorService.clearSensitiveInfo(instructor);
				section.setInstructorId(null);
				section.setInstructor(instructor);
			}
		}
	}

	public boolean validateLocation(String location) {
		return location.matches(locationRegex);
	}

	public boolean validateCapacity(int capacity) {
		return capacity >= 10 && capacity <=300;
	}

	public boolean ifEnrolledStudentsMoreThanCapacity(CourseSection section, int capacity) {
		return section.getEnrolledStudentsId().size() > capacity;
	}
}
