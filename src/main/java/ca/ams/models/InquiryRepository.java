package ca.ams.models;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InquiryRepository extends MongoRepository<Inquiry, String>{

}
