package diet;

import java.util.*;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Represents a takeaway restaurant chain.
 * It allows managing restaurants, customers, and orders.
 */
public class Takeaway {
	HashMap<String, Restaurant> restaurantMap = new HashMap<>();
	ArrayList<Food> foodList = new ArrayList<>();
	ArrayList<Customer> customerList = new ArrayList<>();
	HashMap<Integer, Order> orderMap = new HashMap<>();
	
	int numOrder = 0;
	/**
	 * Constructor
	 * @param food the reference {@link Food} object with materials and products info.
	 */
	public Takeaway(Food food){
		this.foodList.add(food);
	}

	/**
	 * Creates a new restaurant with a given name
	 *
	 * @param restaurantName name of the restaurant
	 * @return the new restaurant
	 */
	public Restaurant addRestaurant(String restaurantName) {
		ArrayList<Hour> apertura = new ArrayList<Hour>();
		this.restaurantMap.put(restaurantName, new Restaurant(restaurantName, apertura));
		return restaurantMap.get(restaurantName);
	}

	/**
	 * Retrieves the names of all restaurants
	 *
	 * @return collection of restaurant names
	 */
	public Collection<String> restaurants() {
		Collection<String> names = new ArrayList<>(this.restaurantMap.keySet());
		return names;
	}

	/**
	 * Creates a new customer for the takeaway
	 * @param firstName first name of the customer
	 * @param lastName	last name of the customer
	 * @param email		email of the customer
	 * @param phoneNumber mobile phone number
	 *
	 * @return the object representing the newly created customer
	 */
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer c = new Customer(firstName, lastName, email, phoneNumber);
		this.customerList.add(c);
		return c;
	}
	


	/**
	 * Retrieves all registered customers
	 *
	 * @return sorted collection of customers
	 */
	public Collection<Customer> customers(){	
		List<Customer> customerListSorted = this.customerList.stream().sorted((a, b) -> (a.getLastName() + a.getFirstName()).compareTo(b.getLastName() + b.getFirstName())).collect(Collectors.toList());
		return customerListSorted;
	}


	/**
	 * Creates a new order for the chain.
	 *
	 * @param customer		 customer issuing the order
	 * @param restaurantName name of the restaurant that will take the order
	 * @param time	time of desired delivery
	 * @return order object
	 */
	public Order createOrder(Customer customer, String restaurantName, String time) {
		Boolean isOpen = new Hour(Integer.parseInt(time.split(":")[0]), Integer.parseInt(time.split(":")[1])).isOpen(this.restaurantMap.get(restaurantName).getOpening());
		if(isOpen) {
			this.orderMap.put(this.numOrder, new Order(customer, new Hour(Integer.parseInt(time.split(":")[0]), Integer.parseInt(time.split(":")[1])),  this.restaurantMap.get(restaurantName)));
			this.restaurantMap.get(restaurantName).addOrder(this.orderMap.get(numOrder));
		}else {
			this.orderMap.put(this.numOrder, new Order(customer, new Hour(Integer.parseInt(time.split(":")[0]), Integer.parseInt(time.split(":")[1])).nextOpeningHour(this.restaurantMap.get(restaurantName).getOpening()),  this.restaurantMap.get(restaurantName)));
			this.restaurantMap.get(restaurantName).addOrder(this.orderMap.get(numOrder));
		}
		this.numOrder += 1;
		return this.orderMap.get(this.numOrder-1);
	}


	/**
	 * Find all restaurants that are open at a given time.
	 *
	 * @param time the time with format {@code "HH:MM"}
	 * @return the sorted collection of restaurants
	 */
	public Collection<Restaurant> openRestaurants(String time){
		List<Restaurant> restaurantList = this.restaurantMap.values().stream().sorted((a, b)-> a.getName().compareTo(b.getName())).filter(a->(new Hour(time.split(":")[0], time.split(":")[1])).isOpen(a.getOpening())).collect(Collectors.toList());
		return restaurantList;
	}
}
