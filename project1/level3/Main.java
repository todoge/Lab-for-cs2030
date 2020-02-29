import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
class Main{
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		ArrayList<Double> arrivalTimes = new ArrayList<>();
		Server waiter = new Server();
		while(scanner.hasNextDouble()){
			double arrivalTime = scanner.nextDouble();
			arrivalTimes.add(arrivalTime);
		}
		PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());
		int numOfCustomers = arrivalTimes.size();
		for(int i = 0; i < numOfCustomers; i++){
			Customer new_customer = new Customer(i+1,arrivalTimes.get(i));
			events.add(new Event(States.ARRIVES,new_customer,arrivalTimes.get(i)));
			if(waiter.isBusy()){
				if(waiter.servingUntil() <= arrivalTimes.get(i)){
					waiter.free();
				}
				else{	
					events.add(new Event(States.LEAVES,new_customer,arrivalTimes.get(i)));
				}
			}
			if(!waiter.isBusy()){
				waiter.serve(new_customer,arrivalTimes.get(i));
				events.add(new Event(States.SERVED, new_customer, arrivalTimes.get(i)));
			}

		}
		 while(events.size() != 0){
			 System.out.println(events.remove());
		 }
		 System.out.println("Number of customers: " + numOfCustomers);

	}
}


