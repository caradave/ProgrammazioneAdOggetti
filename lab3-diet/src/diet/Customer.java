package diet;


public class Customer {
	String name, surname, email = null, phoneNumber = null;
	
	public Customer(String name, String surname, String email, String phoneNumber) {
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.surname = surname;
	}
	
	public String getLastName() {
		return this.surname;
	}
	
	public String getFirstName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhone() {
		return this.phoneNumber;
	}
	
	public void SetEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phoneNumber = phone;
	}
	
	@Override
	public String toString() {
		return this.getLastName() + " " + this.getFirstName();
	}
	
}
