package ca.ams.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Course {
	@Id
	private String id;
	private String courseId;
	private double credits;
	private String courseName;
	private String courseDescription;
	private String grade;
	private String year;
	private Term term;
	private List<String> prerequisiteCourseIds = new ArrayList<String>();
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
	public List<String> getPrerequisiteCourseIds() {
		return prerequisiteCourseIds;
	}
	public List<CourseSection> getCourseSections() {
		return courseSections;
	}
	public double getCredits() {
		return credits;
	}
	public void setCredits(double credits) {
		this.credits = credits;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}	
	public void setCourseCompleted(String grade, String year, Term term) {
		this.courseSections = null;
		this.prerequisiteCourseIds = null;
		this.courseDescription = null;
		this.setGrade(grade);
		this.setYear(year);
		this.setTerm(term);
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
}
