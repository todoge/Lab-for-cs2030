/**
 * This is a class to create an Event
 *
 * @author peh jun siang
 */
public class Event{
	private final States state;
	private final Customer customer;
	private final Double time;
	/**
	 * Event Constructor to create an event
	 *
	 * @param state is the State of the customer
	 * @see States
	 * @param customer is the current customer in the event
	 * @see Customer
	 * @param time is the time the event is currently happening
	 * @author peh jun siang
	 */
	Event(States state, Customer customer, Double time){
		this.state = state;
		this.customer = customer;
		this.time = time;
	}
	/**
	 * getState returns the state of the Event
	 *
	 * @param void
	 * @see #state
	 * @return state
	 * @author peh jun siang
	 */
	public States getState(){
		return state;
	}

	/**
	 * getCustomer returns the customer in the event
	 *
	 * @param void
	 * @see #customer
	 * @return customer
	 * @author peh jun siang
	 */
	public Customer getCustomer(){
		return customer;
	}

	/**
	 * getTime returns the Time of the event
	 *
	 * @param void
	 * @see #time
	 * @return time
	 * @author peh jun siang
	 */
	public Double getTime(){
		return time;
	}

	/**
	 * Overrides toString method to the stipulated format
	 *
	 * @see #toString()
	 * @return String
	 * @author peh jun siang
	 */
	@Override
	public String toString(){
		return String.format("%.3f",time) + " " + customer.getID() + " " + state;
	}
}
