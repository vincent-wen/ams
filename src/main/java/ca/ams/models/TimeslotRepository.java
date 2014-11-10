package ca.ams.models;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeslotRepository extends MongoRepository<Timeslot, String>{

	public Timeslot findByStartTimeAndEndTime(String startTime, String endTime);
}
