package ca.ams.models;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GradeRepository extends MongoRepository<Grade, String>{
	public Grade findByLetterGrade(String letterGrade);
	public Grade findByNumberGrade(float numberGrade);
}
