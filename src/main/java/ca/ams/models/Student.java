package ca.ams.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Student extends User{
	
	private int studentId;
	private String program;
	@DBRef
	private List<CourseSection> registeredSections = new ArrayList<CourseSection>();
	private HashMap<String, Grade> completedCoursesId = new HashMap<String, Grade>();
	
	public List<CourseSection> getRegisteredSections() {
		return registeredSections;
	}
	public HashMap<String, Grade> getCompletedCourseId() {
		return completedCoursesId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	
}
