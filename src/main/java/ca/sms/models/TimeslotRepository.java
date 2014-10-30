package ca.sms.models;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeslotRepository extends MongoRepository<Timeslot, String>{

	public Timeslot findByStartTime(String startTime);
}
