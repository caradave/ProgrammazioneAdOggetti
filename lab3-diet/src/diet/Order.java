package diet;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order {
	PaymentMethod payM = PaymentMethod.CASH;
	OrderStatus ordS = OrderStatus.ORDERED;
	
	HashMap<String, Integer> menus = new HashMap<>(); 
	Customer customer;
	Hour h;
	Restaurant resturant;
	
	public Order(Customer cus, Hour hour, Restaurant res) {
		this.customer =cus;
		this.h = hour;
		this.resturant = res;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public Hour getHour() {
		return this.h;
	}
	
	public Restaurant getRestaurant() {
		return this.resturant;
	}
	
	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}

	
	
	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		this.payM = pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.payM;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		this.ordS = os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return this.ordS;
	}

	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
	public Order addMenus(String menu, int quantity) {
		this.menus.put(menu, quantity);
		return this;
	}
	
	@Override
	public String toString() {
		String tot = this.resturant.getName() +", "+this.customer.getFirstName()+" "+this.getCustomer().getLastName()+" : ("+this.getHour().toString()+"):";
		List<String> menuList = this.menus.keySet().stream().sorted().collect(Collectors.toList());
		for(String s: menuList) {
			tot += "\n\t" + s + "->" + this.menus.get(s);
		}
		return tot;
	}
	
}
