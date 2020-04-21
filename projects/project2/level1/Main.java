import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
class Main{
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		ArrayList<Double> arrivalTimes = new ArrayList<>();
		while(scanner.hasNextDouble()){
			double arrivalTime = scanner.nextDouble();
			arrivalTimes.add(arrivalTime);
		}
		PriorityQueue<Customer> customers = new PriorityQueue<>(arrivalTimes.size(),new CustomerComparator());
		int numOfCustomers = arrivalTimes.size();
		for(int i = 0; i < numOfCustomers; i++){
			Customer temp = new Customer(i+1,arrivalTimes.get(i));
			System.out.println(temp.toString());
			customers.add(temp);
		}
		System.out.println("Number of customers: " + numOfCustomers); 

	}
}


