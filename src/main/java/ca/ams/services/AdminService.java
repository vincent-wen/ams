package ca.ams.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.*;

@Component
public class AdminService {
	@Autowired
	private UserService userService;
	@Autowired
	private TimeslotRepository timeslotRepos;
	@Autowired
	private CourseSectionRepository sectionRepos;
	@Autowired
	private CourseRepository courseRepos;
	@Autowired
	private GPDRepository gpdRepos;
	@Autowired
	private StudentRepository studentRepos;
	@Autowired
	private ProfessorRepository professorRepos;
	@Autowired
	private RegistrarRepository registrarRepos;

	public void init() {
		if(!studentRepos.findAll().isEmpty()) return;
		Professor professor1 = createProfessor1();
		Professor professor2 = createProfessor2();
		Professor professor3 = createProfessor3();
		createStudent1();
		createGPD1();
		
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
		
		section1.setCourseObjectId(course1.getId());
		section2.setCourseObjectId(course1.getId());
		section3.setCourseObjectId(course2.getId());
		sectionRepos.save(section1);
		sectionRepos.save(section2);
		sectionRepos.save(section3);
	}

	public Student createStudent1() {
		Student student = new Student();
		student.setUsername("vincent");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("vincent.wen77@gmail.com");
		student.setStudentId(6812910);
		student.setProgram("Software Engineering");
		student.setName("Luheng Wen");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-8435");
		return studentRepos.save(student);
	}
	
	public GPD createGPD1() {
		GPD gpd = new GPD();
		gpd.setUsername("goswami");
		gpd.setPassword("111111a");
		userService.encryptPassword(gpd);
		gpd.setName("Dhrubajyoti Goswami");
		gpd.setEmail("goswami@cs.concordia.ca");
		gpd.setPhoneNumber("514-848-2424 ext. 7882");
		gpd.setRole(Role.ROLE_GPD);
		return gpdRepos.save(gpd);
	}
	
	public Professor createProfessor1() {
		Professor professor = new Professor();
		professor.setName("Rachida Dssouli");
		professor.setPassword("111111a");
		userService.encryptPassword(professor);
		professor.setUsername("dssouli");
		professor.setEmail("rachida.dssouli@concordia.ca");
		professor.setRole(Role.ROLE_PROFESSOR);
		professor.setPhoneNumber("514-848-2424 ext. 4162");
		return professorRepos.save(professor);
	}
	
	public Professor createProfessor2() {
		Professor professor = new Professor();
		professor.setName("Gregory Butler");
		professor.setPassword("111111a");
		userService.encryptPassword(professor);
		professor.setUsername("gregb");
		professor.setEmail("gregb@cs.concordia.ca");
		professor.setRole(Role.ROLE_PROFESSOR);
		professor.setPhoneNumber("514-848-2424 ext. 3031");
		return professorRepos.save(professor);
	}
	
	public Professor createProfessor3() {
		Professor professor = new Professor();
		professor.setName("Peter C. Rigby");
		professor.setPassword("111111a");
		userService.encryptPassword(professor);
		professor.setUsername("peter");
		professor.setEmail("peter.rigby@concordia.ca ");
		professor.setRole(Role.ROLE_PROFESSOR);
		return professorRepos.save(professor);
	}

	public void test() {
		List<Student> students = studentRepos.findByNameRegex("lu");
		if(students.isEmpty()) System.out.println("it's null");
		Iterator<Student> iterator = students.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next().getUsername());
		}
	}
	
}
