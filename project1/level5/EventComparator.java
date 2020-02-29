/**
 * Event comparator used to arrange sequence of events according to specification
 *
 * @see java.util.Comparator
 * @author peh jun siang
 */
import java.util.Comparator;
public class EventComparator implements Comparator<Event>{

	/**
	 * Compare method takes in 2 events
	 * Events are sorted from earliest to latest
	 * if both events occur simultaneously then the customer with a smaller ID comes first (FIFO)
	 * if the IDs are the same, events are ordered according to the States
	 * @param e1 is a Event
	 * @param e2 is a Event
	 * @see Event
	 * @see States
	 * @see Customer
	 * @author peh jun siang
	 */
	// compares two customer's arrival time
	@Override
	public int compare(Event e1, Event e2){
		if(e1.getTime() - e2.getTime() == 0){
			Integer e1_ID = e1.getCustomer().getID();
			Integer e2_ID = e2.getCustomer().getID();
			if(e1_ID - e2_ID != 0){
				return e1_ID.compareTo(e2_ID);
			}
			else{
				return e1.getState().getValue().compareTo(e2.getState().getValue());
			}
		}
		else{
			return e1.getTime().compareTo(e2.getTime());
		}
	}

}
