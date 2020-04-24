package cs2030.simulator;
import java.util.PriorityQueue;

public class Engine {

    private static double totalWaitingTime = 0;
    private static int totalCustomersServed = 0;
    private static PriorityQueue<Server> chronologicalOrder;
    private static Server[] servers;
    private static double Time = 0;

    private static boolean isAllBusy() {
        for (int i = 0; i < servers.length; i++) {
            if(!servers[i].isBusy()){
                return false;
            }
        }
        return true;
    }


    public static void run(int seed, int numOfServers, int queueLength, int numOfCustomers, double arrivalRate, double serviceRate) {

        PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());
        servers = new Server[numOfServers];

        chronologicalOrder = new PriorityQueue<>((s1, s2) -> {
            if (s1.servingSince() == null && s2.servingSince() == null) {
                return s1.getId().compareTo(s2.getId());
            } else if (s1.servingSince() == null) {
                return -1;
            } else if (s2.servingSince() == null) {
                return 1;
            } else {
                return s1.servingUntil().compareTo(s2.servingUntil());
            }
        });

        RandomGenerator rng = new RandomGenerator(seed, arrivalRate, serviceRate, 0);

        // set up the servers
        for (int i = 0; i < numOfServers; i++) {
            Server temp = new Server(i + 1, queueLength);
            servers[i] = temp;
        }

// ============================================================================================================
        for (int i = 0; i < numOfCustomers; i++) {

            Customer newCustomer = new Customer(i + 1, Time);
            events.add(new Event(States.ARRIVES, newCustomer, newCustomer.getArrivalTime()));

            // set up the servers
            for (int l = 0; l < numOfServers; l++) {
                chronologicalOrder.add(servers[l]);
            }

            // check if the waiters are busy one by one through the servers array
            // if so see if we can free them
            while (!chronologicalOrder.isEmpty()) {
                // retrieves the server
                Server waiter = chronologicalOrder.poll();
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
                        chronologicalOrder.add(waiter);
                    }
                    // if the server is not busy and there are people queuing after we free the waiter,
                    // we serve the queue of the waiter.
                    } else if (waiter.hasQ() && !waiter.isBusy()) {
                        // we serve the queuing customer
                        Customer nextInLine = waiter.popQ();
                        nextInLine.setServiceTime(rng.genServiceTime());
                        events.add(
                                new Event(States.SERVED, nextInLine,
                                        waiter.servingUntil(), waiter)
                        );
                        totalWaitingTime += (waiter.servingUntil()
                                - nextInLine.getArrivalTime());
                        totalCustomersServed++;
                        waiter.serve(nextInLine, waiter.servingUntil());
                        // then we check if waiter can we freed again
                        if (waiter.servingUntil() <= newCustomer.getArrivalTime()) {
                            chronologicalOrder.add(waiter);
                        }
                    }
            }

// ==============================================================================================================

            // initialise new customer as cannot be serve first.
            boolean cannotBeServed = true;

            // if all servers are busy
            if (isAllBusy()) {
                for (int k = 0; k < numOfServers; k++) {
                    Server waiter = servers[k];
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
                // set up the servers
                for (int l = 0; l < numOfServers; l++) {
                    chronologicalOrder.add(servers[l]);
                }
                for (int j = 0; j < numOfServers; j++) {
                    Server waiter = servers[j];
                    if (!waiter.isBusy()) {
                        // if there are people queuing
                        newCustomer.setServiceTime(rng.genServiceTime());
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
            chronologicalOrder.clear();
            // if cannot be served due to all serves busy and cannot queue, leave
            if (cannotBeServed) {
                events.add(new Event(States.LEAVES,newCustomer,newCustomer.getArrivalTime()));
            }
            Time += rng.genInterArrivalTime();
        }

        // ============================================= NO NEW CUSTOMERS BARRIER ==============================================
        // while there are no new customers, there might be still customers
        // queuing for the waiters

        chronologicalOrder.clear();
        // set up the servers
        for (int l = 0; l < numOfServers; l++) {
            chronologicalOrder.add(servers[l]);
        }

        // load all waiters into the queue if they are not freed
        for (int i = 0; i < numOfServers; i++) {
            Server waiter = servers[i];
            if (waiter.hasQ() || waiter.isBusy()) {
                chronologicalOrder.add(waiter);
            }
        }

        // free up all the waiters first
        while (!chronologicalOrder.isEmpty()) {
            Server waiter = chronologicalOrder.poll();
            if (waiter.isBusy()) {
                Time = waiter.servingUntil();
                events.add(
                        new Event(States.DONE, waiter.servingWho(), waiter.servingUntil(), waiter)
                );
                waiter.free();
            } else if (waiter.hasQ()) {
                Customer nextInLine = waiter.popQ();
                nextInLine.setServiceTime(rng.genServiceTime());
                events.add(
                        new Event(States.SERVED, nextInLine, waiter.servingUntil(), waiter)
                );
                totalWaitingTime += (waiter.servingUntil() - nextInLine.getArrivalTime());
                totalCustomersServed++;
                waiter.serve(nextInLine, waiter.servingUntil());
            }

            if (waiter.hasQ() || waiter.isBusy()) {
                chronologicalOrder.add(waiter);
            }
        }

// ============================================ Analysis and printing BARRIER =========================================

        // Analysis portion and printing
        while (events.size() != 0) {
            System.out.println(events.remove());
        }
        int numberOfCustomersLeft = numOfCustomers - totalCustomersServed;
        Double avgWaitTime = (totalWaitingTime / totalCustomersServed);
        if (Double.isNaN(avgWaitTime)) {
            avgWaitTime = 0.0;
        }
        String averageWaitingTime = String.format("%.3f", avgWaitTime);
        System.out.println(
                "[" + averageWaitingTime + " " + totalCustomersServed +
                        " " + numberOfCustomersLeft + "]"
        );
    }
}

