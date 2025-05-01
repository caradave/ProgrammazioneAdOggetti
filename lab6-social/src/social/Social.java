package social;

import java.security.KeyStore.Entry;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Social {
	
	HashMap<String, Person> personMap = new HashMap<>();
	HashMap<String, ArrayList<String>> groupMap = new HashMap<>();

	/**
	 * Creates a new account for a person
	 * 
	 * @param code	nickname of the account
	 * @param name	first name
	 * @param surname last name
	 * @throws PersonExistsException in case of duplicate code
	 */
	public void addPerson(String code, String name, String surname) throws PersonExistsException {
		if(this.personMap.get(code) != null) {
			throw new PersonExistsException();
		}else {
			this.personMap.put(code, new Person(name, surname, code));
		}
	}

	/**
	 * Retrieves information about the person given their account code.
	 * The info consists in name and surname of the person, in order, separated by blanks.
	 * 
	 * @param code account code
	 * @return the information of the person
	 * @throws NoSuchCodeException
	 */
	public String getPerson(String code) throws NoSuchCodeException {
		if(this.personMap.get(code) == null) {
			throw new NoSuchCodeException();
		}else {
			return this.personMap.get(code).getCode() + " " + this.personMap.get(code).getName() + " " + this.personMap.get(code).getSurname();
		}
	}

	/**
	 * Define a friendship relationship between to persons given their codes.
	 * 
	 * Friendship is bidirectional: if person A is friend of a person B, that means that person B is a friend of a person A.
	 * 
	 * @param codePerson1	first person code
	 * @param codePerson2	second person code
	 * @throws NoSuchCodeException in case either code does not exist
	 */
	public void addFriendship(String codePerson1, String codePerson2)
			throws NoSuchCodeException {
		
		if(this.personMap.containsKey(codePerson1) == false) {
			throw new NoSuchCodeException();
		}else if(this.personMap.containsKey(codePerson2) == false) {
			throw new NoSuchCodeException();
		}else {
			this.personMap.get(codePerson1).addFrind(this.personMap.get(codePerson2));
			this.personMap.get(codePerson2).addFrind(this.personMap.get(codePerson1));
		}
	}

	/**
	 * Retrieve the collection of their friends given the code of a person.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return the list of person codes
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> listOfFriends(String codePerson)
			throws NoSuchCodeException {
		if(this.personMap.get(codePerson) == null)
			throw new NoSuchCodeException();
//		if(this.personMap.get(codePerson).checkEmpty() == false) {
//			throw new NoSuchCodeException();
//		}
		return this.personMap.get(codePerson).codeCollection();
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriends(String codePerson)
			throws NoSuchCodeException {
		
		ArrayList<String> tot = new ArrayList<>();
		
		if(this.personMap.get(codePerson) == null)
			throw new NoSuchCodeException();
		
		if(this.personMap.get(codePerson).checkEmpty() == false)
			return tot;
		
		ArrayList<String> friends = (ArrayList<String>)this.personMap.get(codePerson).codeCollection();
		
		for(String s: friends) {
			ArrayList<String> f = new ArrayList<>();
			if(this.personMap.get(s).checkEmpty() == true) {
				f = (ArrayList<String>)this.personMap.get(s).codeCollection();
				for(String si: f) {
					tot.add(si);
				}
			}
		}
		tot.remove(codePerson);
		return tot;
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * The result has no duplicates.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson)
			throws NoSuchCodeException {
		HashSet<String> tot1 = new HashSet<>();
		
		if(this.personMap.get(codePerson) == null)
			throw new NoSuchCodeException();
		
		if(this.personMap.get(codePerson).checkEmpty() == false) {
			return tot1;
		}
		ArrayList<String> friends = (ArrayList<String>)this.personMap.get(codePerson).codeCollection();
		
		for(String s: friends) {
			if(this.personMap.get(s).checkEmpty() == true)
				for(String si : (ArrayList<String>)this.personMap.get(s).codeCollection())
					tot1.add(si);
		}
		
		tot1.remove(codePerson);
		return tot1;
	}

	/**
	 * Creates a new group with the given name
	 * 
	 * @param groupName name of the group
	 */
	public void addGroup(String groupName) {
		this.groupMap.put(groupName, new ArrayList<>());

	}

	/**
	 * Retrieves the list of groups.
	 * 
	 * @return the collection of group names
	 */
	public Collection<String> listOfGroups() {
		return this.groupMap.keySet();
	}

	/**
	 * Add a person to a group
	 * 
	 * @param codePerson person code
	 * @param groupName  name of the group
	 * @throws NoSuchCodeException in case the code or group name do not exist
	 */
	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
		if(this.groupMap.containsKey(groupName) == false || this.personMap.containsKey(codePerson) == false) {
			throw new NoSuchCodeException();
		}
		this.groupMap.get(groupName).add(codePerson);
	}

	/**
	 * Retrieves the list of people on a group
	 * 
	 * @param groupName name of the group
	 * @return collection of person codes
	 */
	public Collection<String> listOfPeopleInGroup(String groupName) {
		return this.groupMap.get(groupName);
	}

	/**
	 * Retrieves the code of the person having the largest
	 * group of friends
	 * 
	 * @return the code of the person
	 */
	public String personWithLargestNumberOfFriends() {
		HashMap<String, Integer> numFriendMap = this.personMap.values().stream().collect(Collectors.toMap(p->p.getCode(), p->p.codeCollection().size(), (a, b) -> a, HashMap<String, Integer>::new));
		HashMap<Integer, String> invMap = numFriendMap.entrySet().stream().collect(Collectors.toMap(e->e.getValue(), e->e.getKey(), (a, b) -> a, HashMap<Integer, String>::new));
		String res = invMap.entrySet().stream().max((e1, e2)->{
			if(e1.getKey() > e2.getKey()) {
				return 1;
			}else {
				return -1;
			}
		}).map(e->e.getValue()).get();
		return res;
	}

	/**
	 * Find the code of the person with largest number
	 * of second level friends
	 * 
	 * @return the code of the person
	 */
	public String personWithMostFriendsOfFriends() {
		Map<String, Integer> totMap = this.personMap.values().stream().collect(Collectors.toMap(x->x.getCode(), x->{
			if(x.codeCollection().size() == 0) {
				return 0;
			}else {
				int tot = 0;
				for(String s: x.codeCollection()) {
					tot += this.personMap.get(s).codeCollection().size();
				}
				return tot;
			}
		}));
		
		
		return totMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
	}

	/**
	 * Find the name of group with the largest number of members
	 * 
	 * @return the name of the group
	 */
	public String largestGroup() {
		
		return this.groupMap.entrySet().stream().max(Comparator.comparing(x->x.getValue().size())).get().getKey();
	}

	/**
	 * Find the code of the person that is member of
	 * the largest number of groups
	 * 
	 * @return the code of the person
	 */
	public String personInLargestNumberOfGroups() {
		
		HashMap<String, Integer> person = new HashMap<>();
		
		for(Person p: this.personMap.values()) {
			person.put(p.getCode(), 0);
			for(ArrayList<String> m: this.groupMap.values()) {
				if(m.contains(p.getCode())) {
					person.replace(p.getCode(), person.get(p.getCode())+1);
				}
			}
		}
		
		return person.entrySet().stream().max(Comparator.comparing(x->(x).getValue())).get().getKey();
	}
}
