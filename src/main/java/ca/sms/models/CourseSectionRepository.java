package ca.sms.models;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseSectionRepository extends MongoRepository<CourseSection, String>{

	public List<CourseSection> findByCourseId(String courseId);
}
