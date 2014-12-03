package ca.ams.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
	private List<Course> completedCourses = new ArrayList<Course>();
	@Transient
	private BigDecimal tuition = new BigDecimal("6000.00");
	private BigDecimal alreadyPaid = new BigDecimal(0);
	@Transient
	private BigDecimal penalty = new BigDecimal(0);
	
	public Student() {
		updatePenalty();
	}
	
	public void updatePenalty() {
		Calendar now = Calendar.getInstance();
		
		int delayMonths = now.get(Calendar.MONTH) - Calendar.SEPTEMBER;
		if(delayMonths > 0 && !this.tuition.add(this.penalty).subtract(this.alreadyPaid).equals(0)) {
			this.penalty = new BigDecimal(delayMonths * 75);
		}
	}
	
	public List<CourseSection> getRegisteredSections() {
		return registeredSections;
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
	public BigDecimal getTuition() {
		return tuition;
	}
	public void setTuition(BigDecimal tuition) {
		this.tuition = tuition;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getAlreadyPaid() {
		return alreadyPaid;
	}

	public void setAlreadyPaid(BigDecimal alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}

	public List<Course> getCompletedCourses() {
		return completedCourses;
	}
	
}
