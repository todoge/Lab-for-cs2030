import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
class Main{
	private static Server waiter = new Server();
	private static double totalWaitingTime = 0;
	private static int totalCustomersServed = 0;
	private static int maxWaitingCustomers = 1;

	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		ArrayList<Double> arrivalTimes = new ArrayList<>();
		while(scanner.hasNextDouble()){
			double arrivalTime = scanner.nextDouble();
			arrivalTimes.add(arrivalTime);
		}

		PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());
		ArrayList<Customer> waitingQueue = new ArrayList<>();

		int numOfCustomers = arrivalTimes.size();

		for(int i = 0; i < numOfCustomers; i++){
			Customer new_customer = new Customer(i+1,arrivalTimes.get(i));
			events.add(new Event(States.ARRIVES,new_customer,arrivalTimes.get(i)));
			// if waiter can be freed, we free the waiter
			if(waiter.isBusy()) {
				if (waiter.servingUntil() <= arrivalTimes.get(i)) {
					events.add(new Event(States.DONE, waiter.getServing(), waiter.servingUntil()));
					waiter.free();
					// if there are people queuing
					while(waitingQueue.size() != 0 && !waiter.isBusy()){
						// we serve the queuing customer
						Customer nextInLine = waitingQueue.remove(0);
						events.add(new Event(States.SERVED, nextInLine,waiter.servingUntil()));
						totalWaitingTime += (waiter.servingUntil() -  nextInLine.getArrivalTime());
						totalCustomersServed++;
						waiter.serve(nextInLine,waiter.servingUntil());
						// then we check if waiter can we freed again
						if (waiter.servingUntil() <= arrivalTimes.get(i)) {
							events.add(new Event(States.DONE, waiter.getServing(), waiter.servingUntil()));
							waiter.free();
						}
					}
				}
			}

			if(waiter.isBusy()){
					// if there are people waiting, leave
					if(waitingQueue.size() >= maxWaitingCustomers){
						events.add(new Event(States.LEAVES,new_customer,arrivalTimes.get(i)));
					}
					// if no one is waiting, wait
					else{
						events.add(new Event(States.WAITS,new_customer,arrivalTimes.get(i)));
						waitingQueue.add(new_customer);
					}
			}

			// if waiter is not busy
			else {
				// if there are people queuing
				waiter.serve(new_customer,arrivalTimes.get(i));
				totalCustomersServed++;
				events.add(new Event(States.SERVED, new_customer, arrivalTimes.get(i)));
			}
		}
		// if waiter is still busy
		while(waiter.isBusy()) {
			events.add(new Event(States.DONE, waiter.getServing(), waiter.servingUntil()));
			waiter.free();
			if(waitingQueue.size() != 0) {
				Customer nextInLine = waitingQueue.remove(0);
				totalWaitingTime += (waiter.servingUntil() -  nextInLine.getArrivalTime());
				totalCustomersServed++;
				events.add(new Event(States.SERVED, nextInLine, waiter.servingUntil()));
				waiter.serve(nextInLine, waiter.servingUntil());
			}
		}
		while(events.size() != 0){
			System.out.println(events.remove());
		}
		int numberOfCustomersLeft = numOfCustomers - totalCustomersServed;
		String averageWaitingTime = String.format("%.3f",totalWaitingTime/totalCustomersServed);
		System.out.println("[" + averageWaitingTime + " " + totalCustomersServed + " " + numberOfCustomersLeft + "]");

	}
}


