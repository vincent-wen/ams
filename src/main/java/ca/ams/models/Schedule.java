package ca.ams.models;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Schedule {
	@Id
	private String id;
	private Time startTime = new Time();
	private Time endTime = new Time();
	private Weekday weekday;
	
	public Schedule(String startTime, String endTime, String weekday) {
		this();
		String[] ss = startTime.split(":");
		String[] es = endTime.split(":");
		this.startTime.setHour(ss[0]);
		this.startTime.setMinute(ss[1]);
		this.endTime.setHour(es[0]);
		this.endTime.setMinute(es[1]);
		this.weekday = Weekday.valueOf(weekday);
	}
	public Schedule() {}
	
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public Weekday getWeekday() {
		return weekday;
	}
	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}
	public boolean ifConflicts(Schedule schedule) {
		if(this.weekday == schedule.weekday &&
				(!this.startTime.after(schedule.startTime) && !this.endTime.before(schedule.startTime) 
						||
				!this.startTime.before(schedule.startTime) && !this.startTime.after(schedule.endTime)
						||
				!this.startTime.before(schedule.startTime) && !this.endTime.after(schedule.endTime)
						||
				!this.startTime.after(schedule.startTime) && !this.endTime.before(schedule.endTime))) {
			return true;
		}
		return false;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((weekday == null) ? 0 : weekday.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (weekday != other.weekday)
			return false;
		return true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
