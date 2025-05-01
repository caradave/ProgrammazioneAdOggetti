package clinic;

public class Doctor {
	private String Name, Surname, SSN, Spec;
	private int Badge;
	
	public Doctor(String name, String surname, String ssn, int badge,  String spec) {
		this.Name = name;
		this.Surname =surname;
		this.SSN = ssn;
		this.Badge = badge;
		this.Spec = spec;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public String getSurname() {
		return this.Surname;
	}
	
	public String getSSN() {
		return this.SSN;
	}
	
	public int getBadge() {
		return this.Badge;
	}
	
	public String getSpec() {
		return this.Spec;
	}
}
