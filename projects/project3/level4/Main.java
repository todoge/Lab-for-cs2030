import java.util.Scanner;
import cs2030.simulator.Engine;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /* sorting the inputs */
        // (int) seed
        int seed = scanner.nextInt();

        // (int) number of servers
        int numOfServers = scanner.nextInt();

        // (int) represent number of self-checkout
        int numOfSelfCheckOut = scanner.nextInt();

        // (int) max queue length
        int queueLength = scanner.nextInt();

        // (int) number of customers
        int numOfCustomers = scanner.nextInt();

        // (double) arrival rate
        double arrivalRate = scanner.nextDouble();

        // (double) service rate
        double serviceRate = scanner.nextDouble();

        // (double) resting rate
        double restingRate = scanner.nextDouble();

        // (double) probability of resting
        double probabilityOfResting = scanner.nextDouble();

        // (double) probability of greedy customer
        double probabilityOfGreedy = scanner.nextDouble();

        Engine.run(seed,numOfServers,numOfSelfCheckOut,queueLength,numOfCustomers,
                arrivalRate, serviceRate, restingRate, probabilityOfResting, probabilityOfGreedy) ;
    }
}
