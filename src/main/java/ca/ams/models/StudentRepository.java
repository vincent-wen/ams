package ca.ams.models;

import java.util.List;

public interface StudentRepository extends UserRepository<Student>{
	public List<Student> findByStudentIdRegex(String studentId);
}