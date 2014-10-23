package ca.sms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.sms.models.Course;
import ca.sms.models.CourseRepository;
import ca.sms.models.CourseSection;
import ca.sms.models.CourseSectionRepository;
import ca.sms.models.Professor;
import ca.sms.models.Timeslot;
import ca.sms.models.TimeslotRepository;

@Component
public class CourseService {
	@Autowired
	private CourseSectionRepository sectionRepos;
	@Autowired
	private CourseRepository courseRepos;
	@Autowired
	private TimeslotRepository timeslotRepos;

	public void saveSection(CourseSection asec) {
		sectionRepos.save(asec);
	}

	public void saveCourse(Course course) {
		courseRepos.save(course);
	}

	public List<Course> getCourseById(String courseId) {
		if(courseId == "") return null;
		String regex = ".*" + courseId + ".*";
		return courseRepos.findByCourseIdRegex(regex);
	}
	
	public List<Course> getCourseByName(String courseName) {
		if(courseName == "") return null;
		String regex = ".*" + courseName + ".*";
		return courseRepos.findByCourseNameRegex(regex);
	}
	
	public List<Course> getAllCourses() {
		return courseRepos.findAll();
	}
	
	public void create1(Professor professor) {
		Timeslot timeslot = new Timeslot();
		timeslot.setStartTime("17:45");
		timeslot.setEndTime("20:15");
		timeslotRepos.save(timeslot);
		
		CourseSection section = new CourseSection();
		section.setLocation("BL-403");
		section.setInstructor(professor);
		section.setTimeslot(timeslot);
		section.setWeekday("Monday");
		section = sectionRepos.save(section);
		
		Course course = new Course();
		course.setCourseDescription("QA course");
		course.setCourseId("INSE6260");
		course.setCourseName("Software Quality Assurance");
		course.getCourseSections().add(section);
		course = courseRepos.save(course);
		
		section.setCourseId(course.getId());
		sectionRepos.save(section);
	}
	
	public void create2(Professor professor) {
		Timeslot timeslot = new Timeslot();
		timeslot.setStartTime("17:45");
		timeslot.setEndTime("20:15");
		timeslotRepos.save(timeslot);
		
		CourseSection section = new CourseSection();
		section.setLocation("EV-803");
		section.setInstructor(professor);
		section.setTimeslot(timeslot);
		section.setWeekday("Tuesday");
		section = sectionRepos.save(section);
		
		Course course = new Course();
		course.setCourseDescription("A course focused on detailed designs.");
		course.setCourseId("SOEN6461");
		course.setCourseName("Software Design Methodology");
		course.getCourseSections().add(section);
		course = courseRepos.save(course);
		
		section.setCourseId(course.getId());
		sectionRepos.save(section);
	}
}
