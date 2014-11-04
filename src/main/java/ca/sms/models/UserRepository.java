package ca.sms.models;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{

	public User findByUsername(String username);

	public User findByFirstNameAndLastName(String firstName, String lastName);
	
	public List<User> findByFirstNameOrLastNameAndRoleRegex(String firstName, String lastName, String role);
}
