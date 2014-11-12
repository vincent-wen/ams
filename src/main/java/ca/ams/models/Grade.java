package ca.ams.models;

public enum Grade {
	Aplus("A+", 4.3), A(4.0), Aminus("A-", 3.7),
	Bplus("B+", 3.3), B(3.0), Bminus("B-", 2.7),
	Cplus("C+", 2.3), C(2.0), Cminus("C-", 1.7),
	F(0);
	
	private double grade;
	private String name;
	
	private Grade(double grade) {
		this.name = this.name();
		this.grade = grade;
	}
	
	private Grade(String name, double grade) {
		this.name = name;
		this.grade = grade;
	}
	
	public String grade() {
		return Double.toString(this.grade);
	}
	
	public String toString() {
		return this.name;
	}
}
