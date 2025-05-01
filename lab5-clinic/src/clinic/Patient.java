package clinic;

public class Patient {
	private String Name, Surname, SSN;
	
	public Patient(String name, String surname, String ssn) {
		this.Name = name;
		this.Surname = surname;
		this.SSN = ssn;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public String getSurname(){
		return this.Surname;
	}
	
	public String getSSN() {
		return this.SSN;
	}
	

}
