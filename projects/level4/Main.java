import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
class Main{
	private static Server waiter = new Server();
	// print out a instance of events
	private static void printInstance(PriorityQueue<Event> queue){
		ArrayList<Event> temp = new ArrayList<>();
		while(queue.size() != 0){
			Event e = queue.poll();
			temp.add(e);
			System.out.println(e);
		}
		for(Event e : temp){
			queue.add(e);
		}
		System.out.println("");
	}

	// takes a priority queue and returns another priority queue with 1 step executed
	private static PriorityQueue<Event> executeOne(PriorityQueue<Event> queue){
		Event first = queue.poll();
		if(first.getState() == States.DONE || first.getState() == States.LEAVES){
			return queue;
		}
		else if(first.getState() == States.SERVED){
			Event event = new Event(States.DONE,first.getCustomer(),first.getTime() + 1);
			queue.add(event);
			return queue;
		}
		else if(waiter.isBusy()){
			if(waiter.servingUntil() <= first.getTime()){
				waiter.free();
			}
			else{
				Event newEvent = new Event(States.LEAVES,first.getCustomer(),first.getTime());
				queue.add(newEvent);
				return queue;
			}
		}
		if(!waiter.isBusy()){
			waiter.serve(first.getCustomer(),first.getTime());
			Event newEvent = new Event(States.SERVED, first.getCustomer(), first.getTime());
			queue.add(newEvent);
			return queue;
		}
		return queue;
	}

	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		ArrayList<Double> arrivalTimes = new ArrayList<>();
		while(scanner.hasNextDouble()){
			double arrivalTime = scanner.nextDouble();
			arrivalTimes.add(arrivalTime);
		}

		PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());
		int numOfCustomers = arrivalTimes.size();
		for(int i = 0; i < numOfCustomers; i++) {
			Customer new_customer = new Customer(i + 1, arrivalTimes.get(i));
			events.add(new Event(States.ARRIVES, new_customer, arrivalTimes.get(i)));
		}
		System.out.println("# Adding arrivals");
		printInstance(events);
		while(events.size()!=0){
			System.out.println("# Get next event: " + events.peek());
			executeOne(events);
			printInstance(events);
		}
		System.out.println("Number of customers: " + numOfCustomers);

	}
}


