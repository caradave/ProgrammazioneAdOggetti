package diet;

import diet.Order.OrderStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	String name;
	ArrayList<Hour> openingHour;
	HashMap<String, Menu> menuMap = new HashMap<>();
	ArrayList<Order> orderList = new ArrayList<>();
	
	public Restaurant(String name, ArrayList<Hour> listRestaurant) {
		this.name = name;
		this.openingHour = listRestaurant;
	}
	
	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		for(String s:hm) {
			String[] s1 = s.split(":");
			this.openingHour.add(new Hour(Integer.parseInt(s1[0]), Integer.parseInt(s1[1])));
		}
	}

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time){
		String[] s1 = time.split(":");
		Hour h = new Hour(Integer.parseInt(s1[0]), Integer.parseInt(s1[1]));
		return h.isOpen(this.openingHour);
	}


	
	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		this.menuMap.put(menu.getName(), menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		return this.menuMap.get(name);
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		String s = "";
		List<Order> orderListSorted = this.orderList.stream().filter(a->a.getStatus() == status).sorted((a, b)-> (a.getRestaurant().getName()+a.getCustomer().getFirstName()+a.getCustomer().getLastName()+a.getHour().toString()).compareTo(b.getRestaurant().getName()+b.getCustomer().getFirstName()+b.getCustomer().getLastName()+b.getHour().toString())).collect(Collectors.toList());
		for(Order o: orderListSorted) {
			if(o.getStatus() == status) {
				s += o.toString();
				s += "\n";
			}
		}
		return s;
	}
	
	public ArrayList<Hour> getOpening(){
		return this.openingHour;
	}
	
	public void addOrder(Order newOrder) {
		this.orderList.add(newOrder);
	}
}
