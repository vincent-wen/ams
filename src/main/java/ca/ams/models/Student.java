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
	
	private String studentId;
	private String program;
	@DBRef
	private List<CourseSection> registeredSections = new ArrayList<CourseSection>();
	// <course object id, grade>
	private HashMap<String, String> completedCoursesId = new HashMap<String, String>();
	
	public List<CourseSection> getRegisteredSections() {
		return registeredSections;
	}
	public HashMap<String, String> getCompletedCoursesId() {
		return completedCoursesId;
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
	
}
