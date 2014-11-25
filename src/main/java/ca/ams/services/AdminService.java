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
		Student student1 = createStudent1();
		createStudent2();
		createStudent3();
		createGPD1();
		createRegistrar1();
		
		Timeslot timeslot1 = new Timeslot();
		timeslot1.setStartTime("17:45");
		timeslot1.setEndTime("20:15");
		timeslotRepos.save(timeslot1);
		
		Timeslot timeslot2 = new Timeslot();
		timeslot2.setStartTime("15:00");
		timeslot2.setEndTime("17:30");
		timeslotRepos.save(timeslot2);
		
		Timeslot timeslot3 = new Timeslot();
		timeslot3.setStartTime("11:45");
		timeslot3.setEndTime("13:00");
		timeslotRepos.save(timeslot3);
		
		CourseSection section1 = new CourseSection();
		section1.setLocation("BL-403");
		section1.setInstructorId(professor1.getId());
		professor1.getInstructedSections().add(section1);
		section1.setTimeslot(timeslot1);
		section1.setWeekday(Weekday.Monday);
		section1 = sectionRepos.save(section1);
		
		CourseSection section2 = new CourseSection();
		section2.setLocation("BL-803");
		section2.setInstructorId(professor2.getId());
		professor2.getInstructedSections().add(section2);
		section2.setTimeslot(timeslot2);
		section2.setWeekday(Weekday.Tuesday);
		section2 = sectionRepos.save(section2);
		
		CourseSection section3 = new CourseSection();
		section3.setLocation("EV-303");
		section3.setInstructorId(professor3.getId());
		professor3.getInstructedSections().add(section3);
		section3.setTimeslot(timeslot1);
		section3.setWeekday(Weekday.Thursday);
		section3 = sectionRepos.save(section3);
		
		CourseSection section4 = new CourseSection();
		section4.setLocation("EV-411");
		section4.setInstructorId(professor3.getId());
		professor3.getInstructedSections().add(section4);
		section4.setTimeslot(timeslot3);
		section4.setWeekday(Weekday.Friday);
		section4 = sectionRepos.save(section4);

		Course course1 = new Course();
		course1.setCourseDescription("Quality assurance, quality factors, components of a software quality assurance system, contract review, software development and quality plans, activities and alternatives, integration of quality activities  in a project lifecycle, reviews, software inspection, software verification,  testing processes, static analysis, control-flow analysis, data-flow analysis, control-flow testing, loop testing, data-flow testing, transaction-flow testing, domain testing, type-based analysis, dynamic analysis, usage models, operational profiles, result and defect analysis, reliability, performance analysis, maintenance and reverse engineering, case tools and software quality assurance. A project.");
		course1.setCourseId("INSE6260");
		course1.setCourseName("Software Quality Assurance");
		course1.getCourseSections().add(section1);
		course1.getCourseSections().add(section2);
		course1.setCredits(4.0);
		course1 = courseRepos.save(course1);
		
		Course course2 = new Course();
		course2.setCourseDescription("Introduction to software design processes and their models. Representations of design/architecture. Software architectures and design plans. Design methods, object-oriented application frameworks, design patterns, design quality and assurance, coupling and cohesion measurements, design verification and documentation. A design project.");
		course2.setCourseId("SOEN6461");
		course2.setCourseName("Software Design Methodology");
		course2.getCourseSections().add(section3);
		course2.setCredits(4.0);
		course2.getPrerequisiteCourseIds().add(course1.getId());
		course2 = courseRepos.save(course2);
		
		Course course3 = new Course();
		course3.setCourseDescription("Problems of writing and managing code. Managing code complexity and quality through a programming process. Coding conventions. Inline software documentation. Software configuration management. Tools and techniques for testing software. Multithreading concurrency. Code reuse in software development. Quality in coding, fault tolerance. A project. Laboratory: two hours per week.");
		course3.setCourseId("SOEN6441");
		course3.setCourseName("Advanced Programming Practices");
		course3.getCourseSections().add(section4);
		course3.setCredits(4.0);
		course3 = courseRepos.save(course3);
		
		Course course4 = new Course();
		course4.setCourseDescription("Role of measurement in Software Engineering, theoretical, technical and managerial views on software measurement. Representational theory of measurement. Theoretical validation of software measurement. Measurement program: goal-driven approach. Collecting and analyzing software engineering data. Software quality modeling and measuring. Testing and measurement. Reliability models. Functional size measurement methods. Effort estimation models and their usage in project management. Software measurement standards. Tool support. A project.");
		course4.setCourseId("SOEN6611");
		course4.setCourseName("Software Measurement");
//		course4.getCourseSections().add(section3);
		course4.setCredits(4.0);
		course4 = courseRepos.save(course4);
		
		section1.setCourseObjectId(course1.getId());
		section2.setCourseObjectId(course1.getId());
		section3.setCourseObjectId(course2.getId());
		section4.setCourseObjectId(course3.getId());
		sectionRepos.save(section1);
		sectionRepos.save(section2);
		sectionRepos.save(section3);
		sectionRepos.save(section4);
		professorRepos.save(professor1);
		professorRepos.save(professor2);
		professorRepos.save(professor3);
		
		student1.getCompletedCoursesAndGrades().put(course3.getId(), Grade.A.toString());
		student1.getCompletedCoursesAndGrades().put(course4.getId(), Grade.Bplus.toString());
		studentRepos.save(student1);
	}

	private Registrar createRegistrar1() {
		Registrar registrar = new Registrar();
		registrar.setUsername("halina");
		registrar.setPassword("111111a");
		userService.encryptPassword(registrar);
		registrar.setEmail("halina@cs.concordia.ca");
		registrar.setRole(Role.ROLE_REGISTRAR);
		registrar.setPhoneNumber("514-848-2424 ext3043");
		registrar.setName("Halina Monkiewicz");
		return registrarRepos.save(registrar);
	}

	public Student createStudent1() {
		Student student = new Student();
		student.setUsername("vincent");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("vincent.wen77@gmail.com");
		student.setStudentId("6812910");
		student.setProgram("Software Engineering");
		student.setName("Luheng Wen");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-8435");
		return studentRepos.save(student);
	}
	
	public Student createStudent2() {
		Student student = new Student();
		student.setUsername("iris");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("irisli77@gmail.com");
		student.setStudentId("1234567");
		student.setProgram("Software Engineering");
		student.setName("Iris Li");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-3245");
		return studentRepos.save(student);
	}
	
	public Student createStudent3() {
		Student student = new Student();
		student.setUsername("ahmad");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("iahmad.ictp@gmail.com");
		student.setStudentId("6283124");
		student.setProgram("Software Engineering");
		student.setName("Ahmad Al-Sheikh Hassan");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0000");
		return studentRepos.save(student);
	}
	
	public GPD createGPD1() {
		GPD gpd = new GPD();
		gpd.setUsername("goswami");
		gpd.setPassword("111111a");
		userService.encryptPassword(gpd);
		gpd.setName("Dhrubajyoti Goswami");
		gpd.setEmail("goswami@cs.concordia.ca");
		gpd.setPhoneNumber("514-848-2424 ext7882");
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
		professor.setPhoneNumber("514-848-2424 ext4162");
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
		professor.setPhoneNumber("514-848-2424 ext3031");
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
		professor.setPhoneNumber("514-848-2424 ext1234");
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
