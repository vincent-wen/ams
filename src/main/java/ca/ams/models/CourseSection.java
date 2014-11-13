package ca.ams.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class CourseSection {
	@Id
	private String id;
	private String courseObjectId;
	@Transient
	private String courseId;
	private String location;

	private String instructorId;
	@Transient
	private Professor instructor;
	private Timeslot timeslot;
	private Weekday weekday;
	@Transient
	private int size;
	private int capacity = 200;
	private List<String> enrolledStudentsId = new ArrayList<String>();
	
	public String getCourseObjectId() {
		return courseObjectId;
	}
	public void setCourseObjectId(String courseObjectId) {
		this.courseObjectId = courseObjectId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}
	public Timeslot getTimeslot() {
		return timeslot;
	}
	public void setTimeslot(Timeslot timeslot) {
		this.timeslot = timeslot;
	}
	public Weekday getWeekday() {
		return weekday;
	}
	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public List<String> getEnrolledStudentsId() {
		return enrolledStudentsId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseSection other = (CourseSection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Professor getInstructor() {
		return instructor;
	}
	public void setInstructor(Professor instructor) {
		this.instructor = instructor;
	}
	public void setEnrolledStudentsId(List<String> enrolledStudentsId) {
		this.enrolledStudentsId = enrolledStudentsId;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
