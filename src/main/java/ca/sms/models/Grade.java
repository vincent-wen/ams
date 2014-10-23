package ca.sms.models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Grade {
	private String letterGrade;
	private float numberGrade;
	public String getLetterGrade() {
		return letterGrade;
	}
	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}
	public float getNumberGrade() {
		return numberGrade;
	}
	public void setNumberGrade(float numberGrade) {
		this.numberGrade = numberGrade;
	}
}
