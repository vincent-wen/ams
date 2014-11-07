package ca.ams.models;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ca.ams.models.Course;

public interface CourseRepository extends MongoRepository<Course, String>{
	@Query("{'courseId':{'$regex': ?0, '$options': 'i'}}")
	public List<Course> findByCourseIdRegex(String courseId);
	
	@Query("{'courseName':{'$regex': ?0, '$options': 'i'}}")
	public List<Course> findByCourseNameRegex(String courseName);
}
