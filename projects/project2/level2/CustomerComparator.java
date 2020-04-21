import java.util.Comparator;
class CustomerComparator implements Comparator<Customer>{

	// compares two customer's arrival time
	public int compare(Customer c1, Customer c2){
		if(c1.getArrivalTime() < c2.getArrivalTime()){
			return 1;
		}
		else if(c1.getArrivalTime() > c2.getArrivalTime()){
			return -1;
		}
		else{
			return 0;
		}
	}
}
