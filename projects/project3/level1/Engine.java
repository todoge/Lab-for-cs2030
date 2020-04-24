package cs2030.simulator;
import java.util.PriorityQueue;

public class Engine {

    private static double totalWaitingTime = 0;
    private static int totalCustomersServed = 0;
    private static Server[] servers;
    private static double Time = 0;

    private static boolean isAllBusy() {
        boolean result = true;
        for (int i = 0; i < servers.length; i++) {
            result = result && servers[i].isBusy();
        }
        return result;
    }

    private static boolean isAnyBusy() {
        for (int i = 0; i < servers.length; i++) {
            if (servers[i].isBusy()){
                return true;
            }
            if (servers[i].hasQ()){
                return true;
            }
        }
        return false;
    }

    public static void run(int seed, int numOfServers, int queueLength, int numOfCustomers, double arrivalRate, double serviceRate) {

        PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());

        RandomGenerator rng = new RandomGenerator(seed, arrivalRate, serviceRate, 0);

        double serviceTimeRequired = 0;

        servers = new Server[numOfServers];

        // set up the servers
        for (int i = 0; i < numOfServers; i++) {
            servers[i] = new Server(i + 1, queueLength);
        }


        for (int i = 0; i < numOfCustomers; i++) {

            serviceTimeRequired = rng.genServiceTime();

            Customer newCustomer = new Customer(i + 1, Time, serviceTimeRequired);
            Time += rng.genInterArrivalTime();



            events.add(new Event(States.ARRIVES, newCustomer, newCustomer.getArrivalTime()));

            // check if the waiters are busy one by one through the servers array
            // if so see if we can free them
            for (int j = 0; j < numOfServers; j++) {

                // retrieves the server
                Server waiter = servers[j];
                // if server is busy we check if server can be freed.
                // Otherwise we will check if customer can queue
                if (waiter.isBusy()) {
                    // check if server can serve
                    if (waiter.servingUntil() <= newCustomer.getArrivalTime()) {
                        // if can serve, we free the current waiter
                        events.add(
                                new Event(States.DONE, waiter.servingWho(),
                                        waiter.servingUntil(), waiter)
                        );
                        // free
                        waiter.free();

                        // if the server is not busy and there are people queuing after we free the waiter,
                        // we serve the queue of the waiter.
                        while (waiter.hasQ() && !waiter.isBusy()) {
                            // we serve the queuing customer
                            Customer nextInLine = waiter.popQ();
                            events.add(
                                    new Event(States.SERVED, nextInLine,
                                            waiter.servingUntil(), waiter)
                            );
                            totalWaitingTime += (waiter.servingUntil()
                                    - nextInLine.getArrivalTime());
                            totalCustomersServed++;
                            waiter.serve(nextInLine, waiter.servingUntil());
                            // then we check if waiter can we freed again
                            if (waiter.servingUntil() <= nextInLine.getArrivalTime()) {
                                events.add(
                                        new Event(States.DONE, waiter.servingWho(),
                                                waiter.servingUntil(), waiter)
                                );
                                waiter.free();
                            }
                        }
                    }
                }
            }
            // initialise new customer as cannot be serve first.
            boolean cannotBeServed = true;
            // if all servers are busy
            if (isAllBusy()) {
                for (int j = 0;  j < numOfServers; j++) {
                    Server waiter = servers[j];
                    // if customer can be queued
                    if (waiter.canQ()) {
                        events.add(new Event(States.WAITS,newCustomer,newCustomer.getArrivalTime(),waiter));
                        waiter.addQ(newCustomer);

                        cannotBeServed = false;
                        break;
                    }
                }
                // otherwise not all servers are busy, we serve the new customer immediately
            } else {
                for (int j = 0; j < numOfServers; j++) {
                    Server waiter = servers[j];
                    if (!waiter.isBusy()) {
                        // if there are people queuing
                        waiter.serve(newCustomer,newCustomer.getArrivalTime());
                        totalCustomersServed++;
                        events.add(
                                new Event(States.SERVED, newCustomer, newCustomer.getArrivalTime(),waiter)
                        );
                        cannotBeServed = false;
                        break;
                    }
                }
            }
            // if cannot be served due to all serves busy and cannot queue, leave
            if (cannotBeServed) {
                events.add(new Event(States.LEAVES,newCustomer,newCustomer.getArrivalTime()));
            }
        }

        // while there are no new customers, there might be still customers
        // queuing for the waiters
        for (int j = 0; j < numOfServers; j++) {
            Server waiter = servers[j];

            // if waiter is still busy
            while (waiter.isBusy()) {
                events.add(
                        new Event(States.DONE, waiter.servingWho(), waiter.servingUntil(),waiter)
                );
                waiter.free();
                if (waiter.hasQ()) {
                    Customer nextInLine = waiter.popQ();
                    totalWaitingTime += (waiter.servingUntil() - nextInLine.getArrivalTime());
                    totalCustomersServed++;
                    events.add(
                            new Event(States.SERVED, nextInLine, waiter.servingUntil(),waiter)
                    );
                    waiter.serve(nextInLine, waiter.servingUntil());
                }
            }
        }

        // Analysis portion and printing
        while (events.size() != 0) {
            System.out.println(events.remove());
        }
        int numberOfCustomersLeft = numOfCustomers - totalCustomersServed;
        String averageWaitingTime = String.format("%.3f",totalWaitingTime / totalCustomersServed);
        System.out.println(
                "[" + averageWaitingTime + " " + totalCustomersServed +
                        " " + numberOfCustomersLeft + "]"
        );
    }
}

