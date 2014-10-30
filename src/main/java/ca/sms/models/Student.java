package ca.sms.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Student extends User{
	
	private int StudentId;
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
		return StudentId;
	}
	public void setStudentId(int studentId) {
		StudentId = studentId;
	}
	
}
