package ca.ams.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.*;

@Component
public class AdminService {
	@Autowired
	private UserService userService;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TimeslotRepository timeslotRepos;
	@Autowired
	private CourseSectionRepository sectionRepos;
	@Autowired
	private CourseRepository courseRepos;
	@Autowired
	private GPDRepository gpdRepos;

	public void init() {
		Professor professor1 = professorService.create("Dssouli");
		Professor professor2 = professorService.create("Peter");
		Professor professor3 = professorService.create("Greb");
		
		Timeslot timeslot1 = new Timeslot();
		timeslot1.setStartTime("17:45");
		timeslot1.setEndTime("20:15");
		timeslotRepos.save(timeslot1);
		Timeslot timeslot2 = new Timeslot();
		timeslot2.setStartTime("15:00");
		timeslot2.setEndTime("17:30");
		timeslotRepos.save(timeslot2);
		
		CourseSection section1 = new CourseSection();
		section1.setLocation("BL-403");
		section1.setInstructor(professor1);
		section1.setTimeslot(timeslot1);
		section1.setWeekday(Weekday.Monday);
		section1 = sectionRepos.save(section1);
		
		CourseSection section2 = new CourseSection();
		section2.setLocation("BL-803");
		section2.setInstructor(professor2);
		section2.setTimeslot(timeslot2);
		section2.setWeekday(Weekday.Tuesday);
		section2 = sectionRepos.save(section2);
		
		CourseSection section3 = new CourseSection();
		section3.setLocation("EV-303");
		section3.setInstructor(professor3);
		section3.setTimeslot(timeslot1);
		section3.setWeekday(Weekday.Thursday);
		section3 = sectionRepos.save(section3);

		Course course1 = new Course();
		course1.setCourseDescription("QA course");
		course1.setCourseId("INSE6260");
		course1.setCourseName("Software Quality Assurance");
		course1.getCourseSections().add(section1);
		course1.getCourseSections().add(section2);
		course1 = courseRepos.save(course1);
		
		Course course2 = new Course();
		course2.setCourseDescription("A course focused on detailed designs.");
		course2.setCourseId("SOEN6461");
		course2.setCourseName("Software Design Methodology");
		course2.getCourseSections().add(section3);
		course2 = courseRepos.save(course2);
		
		section1.setCourseId(course1.getId());
		section2.setCourseId(course1.getId());
		section3.setCourseId(course2.getId());
		sectionRepos.save(section1);
		sectionRepos.save(section2);
		sectionRepos.save(section3);
	}

	public void createStudent() {
		Student student = studentService.create("Luheng", "Wen", 6812910, "vincent.wen77@gmail.com");
		User user = userService.getUser("vincent");
		userService.assignUser(user, student);
	}
	
	public void createGPD() {
		User user = new User();
		user.setFirstName("Dhrubajyoti");
		user.setLastName("Goswami");
		user.setUsername("goswami");
		user.setPassword("111111a");
		userService.encryptPassword(user);
		user.setEmail("goswami@cs.concordia.ca");
		user.setPhoneNumber("514-848-2424; ext. 7882");
		user.setRole("ROLE_GPD");
		
		GPD gpd = new GPD();
		gpd = gpdRepos.save(gpd);
		
		user.setDetailedUser(gpd);
		userService.save(user);
	}
	
}
