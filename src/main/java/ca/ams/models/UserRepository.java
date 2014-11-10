package ca.ams.models;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository<E extends User> extends MongoRepository<E, String>{

	public E findByUsername(String username);
	
	@Query("{'name':{'$regex': ?0, '$options': 'i'}}")
	public List<E> findByNameRegex(String name);
}
