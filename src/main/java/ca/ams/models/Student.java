package ca.ams.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Student extends User{
	
	private String studentId;
	private String program;
	@DBRef
	private List<CourseSection> registeredSections = new ArrayList<CourseSection>();
	// <course object id, grade>
	private HashMap<String, String> completedCoursesAndGrades = new HashMap<String, String>();
	@Transient
	private List<Course> completedCourses = new ArrayList<Course>();
	private BigDecimal tuition = new BigDecimal("6000.00");
	
	public List<CourseSection> getRegisteredSections() {
		return registeredSections;
	}
	public HashMap<String, String> getCompletedCoursesAndGrades() {
		return completedCoursesAndGrades;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public List<Course> getCompletedCourses() {
		return completedCourses;
	}
	public void setCompletedCoursesAndGrades(
			HashMap<String, String> completedCoursesAndGrades) {
		this.completedCoursesAndGrades = completedCoursesAndGrades;
	}
	public BigDecimal getTuition() {
		return tuition;
	}
	public void setTuition(BigDecimal tuition) {
		this.tuition = tuition;
	}
	
}
