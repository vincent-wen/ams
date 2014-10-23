package ca.sms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.sms.models.Grade;
import ca.sms.models.GradeRepository;

@Component
public class GradeService {
	@Autowired
	private GradeRepository gradeRepos;
	
	public float convertToNumberGrade(String letterGrade) {
		Grade grade = gradeRepos.findByLetterGrade(letterGrade);
		return grade.getNumberGrade();
	}
	
	public String convertToLetterGrade(float numberGrade) {
		Grade grade = gradeRepos.findByNumberGrade(numberGrade);
		return grade.getLetterGrade();
	}
}
