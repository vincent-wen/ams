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
		Student student3 = createStudent3();
		createStudent4();
		createStudent5();
		createStudent6();
		createStudent7();
		createStudent8();
		createStudent9();
		createStudent10();
		createStudent11();
		createStudent12();
		createStudent13();
		createStudent14();
		createStudent15();
		createStudent16();
		createStudent17();
		createStudent18();
		createStudent19();
		createStudent20();
		
		createGPD1();
		createRegistrar1();
		
		Schedule schedule1 = new Schedule("17:45", "20:15", "Monday");
		Schedule schedule2 = new Schedule("15:00", "17:30", "Tuesday");
		Schedule schedule3 = new Schedule("11:45", "13:00", "Friday");
				
		CourseSection section1 = new CourseSection();
		section1.setLocation("BL-403");
		section1.setInstructorId(professor1.getId());
		professor1.getInstructedSections().add(section1);
		section1.setSchedule(schedule1);
		section1 = sectionRepos.save(section1);
		
		CourseSection section2 = new CourseSection();
		section2.setLocation("BL-803");
		section2.setInstructorId(professor2.getId());
		professor2.getInstructedSections().add(section2);
		section2.setSchedule(schedule2);
		section2.setCapacity(1);
		section2 = sectionRepos.save(section2);
		
		CourseSection section3 = new CourseSection();
		section3.setLocation("EV-303");
		section3.setInstructorId(professor3.getId());
		professor3.getInstructedSections().add(section3);
		section3.setSchedule(schedule2);
		section3 = sectionRepos.save(section3);
		
		CourseSection section4 = new CourseSection();
		section4.setLocation("EV-411");
		section4.setInstructorId(professor3.getId());
		professor3.getInstructedSections().add(section4);
		section4.setSchedule(schedule3);
		section4 = sectionRepos.save(section4);
		
		CourseSection section5 = new CourseSection();
		section5.setLocation("EV-304");
		section5.setInstructorId(professor2.getId());
		professor2.getInstructedSections().add(section5);
		section5.setSchedule(schedule2);
		section5 = sectionRepos.save(section5);

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
		course4.getCourseSections().add(section5);
		course4.setCredits(4.0);
		course4.getPrerequisiteCourseIds().add(course1.getId());
		course4.getPrerequisiteCourseIds().add(course2.getId());
		course4.getPrerequisiteCourseIds().add(course3.getId());
		course4 = courseRepos.save(course4);
		
		section1.setCourseObjectId(course1.getId());
		section2.setCourseObjectId(course1.getId());
		section3.setCourseObjectId(course2.getId());
		section4.setCourseObjectId(course3.getId());
		section5.setCourseObjectId(course4.getId());
		sectionRepos.save(section1);
		sectionRepos.save(section2);
		sectionRepos.save(section3);
		sectionRepos.save(section4);
		sectionRepos.save(section5);
		professorRepos.save(professor1);
		professorRepos.save(professor2);
		professorRepos.save(professor3);
		
		course3.setCourseCompleted(Grade.A.toString(), "2013-2014", Term.WINTER);
		student3.getCompletedCourses().add(course3);
		course4.setCourseCompleted(Grade.Aplus.toString(), "2014-2015", Term.FALL);
		course2.setCourseCompleted(Grade.Aplus.toString(), "2013-2014", Term.FALL);
		student3.getCompletedCourses().add(course4);
		student3.getCompletedCourses().add(course2);
		studentRepos.save(student3);
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
	
	public Student createStudent4() {
		Student student = new Student();
		student.setUsername("albena");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("albena.ks@gmail.com");
		student.setStudentId("6283125");
		student.setProgram("Software Engineering");
		student.setName("Albena Strupchanska");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0001");
		return studentRepos.save(student);
	}
	
	public Student createStudent5() {
		Student student = new Student();
		student.setUsername("aber");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("aberabozkhar2@gmail.com");
		student.setStudentId("6283126");
		student.setProgram("Software Engineering");
		student.setName("Aber Abozkhar");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0002");
		return studentRepos.save(student);
	}
	
	public Student createStudent6() {
		Student student = new Student();
		student.setUsername("alej");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("alejasanchez074@gmail.com");
		student.setStudentId("6283127");
		student.setProgram("Software Engineering");
		student.setName("Alejandra Sánchez");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0003");
		return studentRepos.save(student);
	}
	
	public Student createStudent7() {
		Student student = new Student();
		student.setUsername("fahim");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("fahimjan56@gmail.com");
		student.setStudentId("6283128");
		student.setProgram("Software Engineering");
		student.setName("Fahim Durrani");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0004");
		return studentRepos.save(student);
	}
	
	public Student createStudent8() {
		Student student = new Student();
		student.setUsername("prakash");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("prakash163@gmail.com");
		student.setStudentId("6283129");
		student.setProgram("Software Engineering");
		student.setName("Fahim Durrani");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0005");
		return studentRepos.save(student);
	}
	
	public Student createStudent9() {
		Student student = new Student();
		student.setUsername("afshin");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("afshin@gmail.com");
		student.setStudentId("6283130");
		student.setProgram("Software Engineering");
		student.setName("Afshin Somani");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0006");
		return studentRepos.save(student);
	}
	
	public Student createStudent10() {
		Student student = new Student();
		student.setUsername("yarona");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("yarona@gmail.com");
		student.setStudentId("6283131");
		student.setProgram("Software Engineering");
		student.setName("Yarona Liang");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0007");
		return studentRepos.save(student);
	}
	
	public Student createStudent11() {
		Student student = new Student();
		student.setUsername("loay");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("loay@gmail.com");
		student.setStudentId("6283132");
		student.setProgram("Software Engineering");
		student.setName("Loay Gewily");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0008");
		return studentRepos.save(student);
	}
	
	public Student createStudent12() {
		Student student = new Student();
		student.setUsername("david");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("david@gmail.com");
		student.setStudentId("6283133");
		student.setProgram("Software Engineering");
		student.setName("David Lim");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0009");
		return studentRepos.save(student);
	}
	
	public Student createStudent13() {
		Student student = new Student();
		student.setUsername("victor");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("victor@gmail.com");
		student.setStudentId("6283134");
		student.setProgram("Software Engineering");
		student.setName("Victor Soledad");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0010");
		return studentRepos.save(student);
	}
	
	public Student createStudent14() {
		Student student = new Student();
		student.setUsername("yun");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("yun@gmail.com");
		student.setStudentId("6283135");
		student.setProgram("Software Engineering");
		student.setName("Yun Zhang");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0011");
		return studentRepos.save(student);
	}
	
	public Student createStudent15() {
		Student student = new Student();
		student.setUsername("ahmad2");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("ahmad2@gmail.com");
		student.setStudentId("6283135");
		student.setProgram("Software Engineering");
		student.setName("Ahmad Moumneh");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0011");
		return studentRepos.save(student);
	}
	
	public Student createStudent16() {
		Student student = new Student();
		student.setUsername("eric");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("eric@gmail.com");
		student.setStudentId("6283136");
		student.setProgram("Software Engineering");
		student.setName("Eric Bozikian");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0012");
		return studentRepos.save(student);
	}
	
	public Student createStudent17() {
		Student student = new Student();
		student.setUsername("auror");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("auror@gmail.com");
		student.setStudentId("6283137");
		student.setProgram("Software Engineering");
		student.setName("Auror Wen");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0013");
		return studentRepos.save(student);
	}
	
	public Student createStudent18() {
		Student student = new Student();
		student.setUsername("rudi");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("rudi@gmail.com");
		student.setStudentId("6283138");
		student.setProgram("Software Engineering");
		student.setName("Rudi Fedelmid");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0014");
		return studentRepos.save(student);
	}
	
	public Student createStudent19() {
		Student student = new Student();
		student.setUsername("coby");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("coby@gmail.com");
		student.setStudentId("6283139");
		student.setProgram("Software Engineering");
		student.setName("Coby Liu");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0015");
		return studentRepos.save(student);
	}
	
	public Student createStudent20() {
		Student student = new Student();
		student.setUsername("rachel");
		student.setPassword("111111a");
		userService.encryptPassword(student);
		student.setEmail("rachel@gmail.com");
		student.setStudentId("6283140");
		student.setProgram("Software Engineering");
		student.setName("Rachel Lim");
		student.setRole(Role.ROLE_STUDENT);
		student.setPhoneNumber("514-430-0016");
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
