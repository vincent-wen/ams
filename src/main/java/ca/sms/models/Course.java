package ca.sms.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Course {
	@Id
	private String id;
	private String courseId;
	private String courseName;
	private String courseDescription;
	@DBRef
	private List<Course> prerequisites = new ArrayList<Course>();
	@DBRef
	private List<CourseSection> courseSections = new ArrayList<CourseSection>();
	
	public String getId() {
		return id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public List<Course> getPrerequisites() {
		return prerequisites;
	}
	public List<CourseSection> getCourseSections() {
		return courseSections;
	}	
	
}
