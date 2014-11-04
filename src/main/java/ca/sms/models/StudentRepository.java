package ca.sms.models;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String>{
	public List<Student> findByStudentIdRegex(int studentId);
}