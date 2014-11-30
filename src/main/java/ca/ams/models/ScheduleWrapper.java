package ca.ams.models;

public class ScheduleWrapper {
	private String sectionObjectId;
	private String startTime;
	private String endTime;
	private String weekday;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getSectionObjectId() {
		return sectionObjectId;
	}
	public void setSectionObjectId(String sectionObjectId) {
		this.sectionObjectId = sectionObjectId;
	}
}
