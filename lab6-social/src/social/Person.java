package social;

import java.util.ArrayList;
import java.util.Collection;

public class Person {
	private String Name, Surname, Code;
	private ArrayList<Person> friendsList = new ArrayList<>();
	
	public Person(String name, String surname, String code) {
		this.Code = code;
		this.Name = name;
		this.Surname = surname;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public String getSurname() {
		return this.Surname;
	}
	
	public String getCode() {
		return this.Code;
	}
	
	public void addFrind(Person friend) {
		this.friendsList.add(friend);
	}
	
	public Collection<String> codeCollection(){
		ArrayList <String> friends = new ArrayList<>();
		for(Person p: this.friendsList) {
			friends.add(p.getCode());
		}
		return friends;
	}
	
	public Boolean checkEmpty() {
		if(this.friendsList.size() == 0) {
			return false;
		}
		return true;
	}
}
