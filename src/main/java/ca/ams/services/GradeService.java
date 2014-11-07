package ca.ams.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ams.models.Grade;
import ca.ams.models.GradeRepository;

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
