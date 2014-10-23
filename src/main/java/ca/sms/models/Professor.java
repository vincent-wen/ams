package ca.sms.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Professor extends User{
	
	private List<CourseSection> instructedSections = new ArrayList<CourseSection>();

	public List<CourseSection> getInstructedSections() {
		return instructedSections;
	}
	
}
