import cs2030.simulator.Engine;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /* sorting the inputs */
        // (int) seed
        int seed = scanner.nextInt();
        // (int) number of servers
        int numOfServers = scanner.nextInt();
        // (int) max queue length
        int queueLength = scanner.nextInt();
        // (int) number of customers
        int numOfCustomers = scanner.nextInt();
        // (double) arrival rate
        double arrivalRate = scanner.nextDouble();
        // (double) service rate
        double serviceRate = scanner.nextDouble();

        Engine.run(seed,numOfServers,queueLength,numOfCustomers,arrivalRate,serviceRate);
    }
}
