package ca.sms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.sms.models.*;

@Component
public class CourseService {
	@Autowired
	private CourseSectionRepository sectionRepos;
	@Autowired
	private CourseRepository courseRepos;
	@Autowired
	private TimeslotRepository timeslotRepos;

	public List<Course> getCoursesById(String courseId) {
		if(courseId == "") return null;
		String regex = ".*" + courseId + ".*";
		return courseRepos.findByCourseIdRegex(regex);
	}
	
	public List<Course> getCoursesByName(String courseName) {
		if(courseName == "") return null;
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

	public Course getCourseById(String courseId) {
		return courseRepos.findOne(courseId);
	}

	public boolean isSectionFull(CourseSection section) {
		return section.getEnrolledStudentsId().size() == section.getSize();
	}

	public Timeslot getTimeslotByStartTime(String startTime) {
		return timeslotRepos.findByStartTime(startTime);
	}
	
}
