import java.util.Comparator;
public class EventComparator implements Comparator<Event>{

	// compares two customer's arrival time
	@Override
	public int compare(Event e1, Event e2){
		if(e1.getTime() == e2.getTime()){
			return e1.getState().getValue().compareTo(e2.getState().getValue());
		}
		else{
			return e1.getTime().compareTo(e2.getTime());
		}
	}

}
