package ca.ams.models;

public enum Grade {
	Aplus(4.3), A(4.0), Aminus(3.7),
	Bplus(3.3), B(3.0), Bminus(2.7),
	Cplus(2.3), C(2.0), Cminus(1.7),
	F(0);
	
	private double grade;
	private Grade(double grade) {
		this.grade = grade;
	}
	public String toString() {
		return Double.toString(this.grade);
	}
}
