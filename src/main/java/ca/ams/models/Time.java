package ca.ams.models;

public class Time implements Comparable<Time>{
	private String hour;
	private String minute;
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	@Override
	public int compareTo(Time t) {
		if(Integer.parseInt(this.hour) > Integer.parseInt(t.getHour())) {
			return 1;
		}
		if(Integer.parseInt(this.hour) < Integer.parseInt(t.getHour())) {
			return -1;
		}
		if(Integer.parseInt(this.minute) > Integer.parseInt(t.getMinute())) {
			return 1;
		}
		if(Integer.parseInt(this.minute) < Integer.parseInt(t.getMinute())) {
			return -1;
		}
		return 0;
	}
	public boolean before(Time t) {
		return this.compareTo(t) == -1;
	}
	public boolean after(Time t) {
		return this.compareTo(t) == 1;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hour == null) ? 0 : hour.hashCode());
		result = prime * result + ((minute == null) ? 0 : minute.hashCode());
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
		Time other = (Time) obj;
		if (hour == null) {
			if (other.hour != null)
				return false;
		} else if (!hour.equals(other.hour))
			return false;
		if (minute == null) {
			if (other.minute != null)
				return false;
		} else if (!minute.equals(other.minute))
			return false;
		return true;
	}
	
}
