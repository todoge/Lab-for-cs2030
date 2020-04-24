package cs2030.simulator;
import java.util.PriorityQueue;
public class Engine {

    private static double totalWaitingTime = 0;
    private static int totalCustomersServed = 0;
    private static Server[] servers;
    private static double Time = 0;
    private static PriorityQueue<Server> chronologicalOrder;
    public static RandomGenerator rng;
    private static int numOfHumanServers;

    private static boolean isAllUnavailable() {
        for (int i = 0; i < servers.length; i++) {
            if(servers[i].canServe()){
                return false;
            }
        }
        return true;
    }

    private static void updateRestStatus(Server waiter, double restingProb){
        // check if waiter takes a break
        if (!waiter.isSelfCheckOut() && rng.genRandomRest() < restingProb) {
            waiter.rest(rng.genRestPeriod());
        }
    }

    private static void updateBackStatus(Server waiter, double time){
        // check if waiter takes a break
        if (!waiter.isSelfCheckOut() && !waiter.isBack() && waiter.servingUntil() <= time) {
            waiter.back();
        } else if (waiter.isSelfCheckOut()) {
            waiter.back();
        }
    }

    private static boolean canCloseStore() {
        for (int k = 0; k < servers.length; k++) {
            Server waiter = servers[k];
            if (waiter.isBusy() || waiter.hasQ()){
                return false;
            }
        }
        return true;
    }

    private static boolean isSelfCheckOutAvaliable() {
        for (int i = numOfHumanServers; i < servers.length; i++) {
            if (!servers[i].isBusy()) {
                return true;
            }
        }
        return false;
    }

    public static void run(int seed, int numOfServers, int numberOfSelfCheckOut, int queueLength, int numOfCustomers,
                           double arrivalRate, double serviceRate, double restingRate, double restingProb) {

        PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());

        numOfHumanServers = numOfServers;

        // seed random generator
        rng = new RandomGenerator(seed, arrivalRate, serviceRate, restingRate);

        servers = new Server[numOfServers + numberOfSelfCheckOut];

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


        // set up the servers
        for (int i = 0; i < numOfServers; i++) {
            Server temp = new Server(i + 1, queueLength);
            servers[i] = temp;
        }
        for (int i = 0; i < numberOfSelfCheckOut; i++){
            if (i == 0) {
                servers[numOfServers] = new SelfCheckOut(numOfServers+1,queueLength);
            } else {
                servers[i+numOfServers] = new SelfCheckOut(numOfServers + 1+i,0);
            }
        }

// ==============================================================================================================

        for (int i = 0; i < numOfCustomers; i++) {

            Customer newCustomer = new Customer(i + 1, Time);
            events.add(new Event(States.ARRIVES, newCustomer, Time));

            // set up the servers
            for (int l = 0; l < numOfServers + numberOfSelfCheckOut; l++) {
                chronologicalOrder.add(servers[l]);
            }

            // check if the waiters are busy one by one
            // see if we can free them
            while (!chronologicalOrder.isEmpty()) {
                // retrieves the server
                Server waiter = chronologicalOrder.poll();
                updateBackStatus(waiter, newCustomer.getArrivalTime());
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
                        updateRestStatus(waiter, restingProb);
                        updateBackStatus(waiter, newCustomer.getArrivalTime());
                        chronologicalOrder.add(waiter);
                    }
                    // if the server is not busy and there are people queuing after we free the waiter,
                    // we serve the queue of the waiter.
                } else if (waiter.hasQ() && waiter.canServe() && !waiter.isSelfCheckOut()) {
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
                } else if ((waiter.isSelfCheckOut() && servers[numOfServers].hasQ() && waiter.canServe())) {
                    // we serve the queuing customer
                    Customer nextInLine = servers[numOfServers].popQ();
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
// ====================== ABSTRACTION BARRIER (ASSUME THAT ALL SERVER QUEUES ARE UPDATED) =============================

            // initialise new customer as cannot be serve first.
            boolean cannotBeServed = true;
            // if all servers are busy
            if (isAllUnavailable()) {
                for (int k = 0; k < numOfServers + numberOfSelfCheckOut; k++) {
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
                for (int j = 0; j < numOfServers + numberOfSelfCheckOut; j++) {
                    Server waiter = servers[j];
                    if (waiter.canServe()) {
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
                events.add(new Event(States.LEAVES, newCustomer, newCustomer.getArrivalTime()));
            }
            Time += rng.genInterArrivalTime();
        }

// ============================================= NO NEW CUSTOMERS BARRIER ==============================================
        // while there are no new customers, there might be still customers
        // queuing for the waiters

        chronologicalOrder.clear();
        // load all waiters into the queue if they are not freed
        for (int i = 0; i < numOfServers + numberOfSelfCheckOut; i++) {
            Server waiter = servers[i];
            chronologicalOrder.add(waiter);
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
                updateRestStatus(waiter, restingProb);

            } else if (waiter.hasQ() && !waiter.isSelfCheckOut()) {
                Customer nextInLine = waiter.popQ();
                nextInLine.setServiceTime(rng.genServiceTime());
                events.add(
                        new Event(States.SERVED, nextInLine, waiter.servingUntil(), waiter)
                );
                totalWaitingTime += (waiter.servingUntil() - nextInLine.getArrivalTime());
                totalCustomersServed++;
                waiter.serve(nextInLine, waiter.servingUntil());
            } else if ((waiter.isSelfCheckOut() && servers[numOfServers].hasQ() && waiter.canServe())) {
                // we serve the queuing customer
                Customer nextInLine = servers[numOfServers].popQ();
                nextInLine.setServiceTime(rng.genServiceTime());
                events.add(
                        new Event(States.SERVED, nextInLine,
                                waiter.servingUntil(), waiter)
                );
                totalWaitingTime += (waiter.servingUntil()
                        - nextInLine.getArrivalTime());
                totalCustomersServed++;
                waiter.serve(nextInLine, waiter.servingUntil());
            }
            if (waiter.hasQ() || waiter.isBusy()) {
                chronologicalOrder.add(waiter);
            } else if (waiter.isSelfCheckOut() && servers[numOfServers].hasQ()){
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
