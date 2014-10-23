package ca.sms.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Student extends User{
	
	private List<CourseSection> registeredSection = new ArrayList<CourseSection>();
	private HashMap<Course, Grade> completedCourse = new HashMap<Course, Grade>();
	public List<CourseSection> getRegisteredSection() {
		return registeredSection;
	}
	public HashMap<Course, Grade> getCompletedCourse() {
		return completedCourse;
	}
	
}
