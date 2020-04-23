package cs2030.simulator;
import java.util.PriorityQueue;
public class Engine {

    private static double totalWaitingTime = 0;
    private static int totalCustomersServed = 0;
    private static Server[] servers;
    private static double Time = 0;
    public static RandomGenerator rng;

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
        if (rng.genRandomRest() < restingProb) {
            waiter.rest(rng.genRestPeriod());
        }
    }

    private static void updateBackStatus(Server waiter, double time){
        // check if waiter takes a break
        if (!waiter.isBack() && waiter.servingUntil() <= time) {
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

    public static void run(int seed, int numOfServers, int queueLength, int numOfCustomers,
                           double arrivalRate, double serviceRate, double restingRate, double restingProb) {

        PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());

        // seed random generator
        rng = new RandomGenerator(seed, arrivalRate, serviceRate, restingRate);

        servers = new Server[numOfServers];
        // set up the servers
        for (int i = 0; i < numOfServers; i++) {
            servers[i] = new Server(i + 1, queueLength);
        }

        for (int i = 0; i < numOfCustomers; i++) {

            Customer newCustomer = new Customer(i + 1, Time);
            events.add(new Event(States.ARRIVES, newCustomer, Time));
            // check if the waiters are busy one by one through the servers array
            // if so see if we can free them
            for (int j = 0; j < numOfServers; j++) {
                // retrieves the server
                Server waiter = servers[j];
                updateBackStatus(waiter, newCustomer.getArrivalTime());
                // if server can serve, serve its queue
                // otherwise, free waiter if can be freed
                // check if server is busy
                if (waiter.isBusy()) {
                    if (waiter.servingUntil() <= Time) {
                        // if can serve, we free the current waiter
                        events.add(
                                new Event(States.DONE, waiter.servingWho(),
                                        waiter.servingUntil(), waiter)
                        );
                        // free waiter
                        waiter.free();
                        updateRestStatus(waiter, restingProb);
                        updateBackStatus(waiter, newCustomer.getArrivalTime());
                    }
                }
                // if the server is not busy and there are people queuing after we free the waiter,
                // we serve the queue of the waiter.
                while (waiter.hasQ() && waiter.canServe()) {
                    // we serve the queuing customer
                    Customer nextInLine = waiter.popQ();
                    nextInLine.setServiceTime(rng.genServiceTime());
                    events.add(
                            new Event(States.SERVED, nextInLine,
                                    waiter.servingUntil(), waiter)
                    );
                    totalWaitingTime += (waiter.servingUntil() - nextInLine.getArrivalTime());
                    totalCustomersServed++;
                    waiter.serve(nextInLine, waiter.servingUntil());
                    // then we check if waiter can we freed again
                    if (waiter.servingUntil() <= Time) {
                        events.add(
                                new Event(States.DONE, waiter.servingWho(),
                                        waiter.servingUntil(), waiter)
                        );
                        waiter.free();
                        updateRestStatus(waiter, restingProb);
                        updateBackStatus(waiter, newCustomer.getArrivalTime());
                    }
                }
            }
// ====================== ABSTRACTION BARRIER (ASSUME THAT ALL SERVER QUEUES ARE UPDATED) =============================

            // initialise new customer as cannot be serve first.
            boolean cannotBeServed = true;
            // if all servers are busy or resting, then wait
            if (isAllUnavailable()) {
                for (int j = 0; j < numOfServers; j++) {
                    Server waiter = servers[j];
                    // if customer can be queued
                    if (waiter.canQ()) {
                        events.add(new Event(States.WAITS, newCustomer, newCustomer.getArrivalTime(), waiter));
                        waiter.addQ(newCustomer);
                        cannotBeServed = false;
                        break;
                    }
                }
                // otherwise not all servers are busy
                // we serve the customer if server is not resting
            } else {
                for (int j = 0; j < numOfServers; j++) {
                    Server waiter = servers[j];
                    updateBackStatus(waiter, newCustomer.getArrivalTime());
                    // waiter is back
                    if (waiter.canServe()) {
                        // if there are people queuing
                        newCustomer.setServiceTime(rng.genServiceTime());
                        waiter.serve(newCustomer, newCustomer.getArrivalTime());
                        totalCustomersServed++;
                        events.add(
                                new Event(States.SERVED, newCustomer, newCustomer.getArrivalTime(), waiter)
                        );
                        cannotBeServed = false;
                        break;
                    }
                }
            }
            // if cannot be served due to all serves busy and cannot queue, leave
            if (cannotBeServed) {
                events.add(new Event(States.LEAVES, newCustomer, newCustomer.getArrivalTime()));
            }
            Time += rng.genInterArrivalTime();
        }

// ============================================= NO NEW CUSTOMERS BARRIER ==============================================
        // while there are no new customers, there might be still customers
        // queuing for the waiters

        PriorityQueue<Server> chronologicalOrder = new PriorityQueue<>((s1, s2) -> s1.servingUntil().compareTo(s2.servingUntil()));
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
                updateRestStatus(waiter, restingProb);

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
        while (events.size() != 0) {
            System.out.println(events.poll());
        }
        int numberOfCustomersLeft = numOfCustomers - totalCustomersServed;
        String averageWaitingTime = String.format("%.3f",totalWaitingTime / totalCustomersServed);
        System.out.println(
                "[" + averageWaitingTime + " " + totalCustomersServed +
                        " " + numberOfCustomersLeft + "]"
        );
    }
}
