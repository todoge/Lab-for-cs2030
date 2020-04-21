import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

class Main {
    private static double totalWaitingTime = 0;
    private static int totalCustomersServed = 0;
    private static Server[] servers;

    private static boolean isAllBusy() {
        boolean result = true;
        for (int i = 0; i < servers.length; i++) {
            result = result && servers[i].isBusy();
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> arrivalTimes = new ArrayList<>();
        int numOfServers = scanner.nextInt();
        servers = new Server[numOfServers];
        // set up the servers
        for (int i = 0; i < numOfServers; i++) {
            servers[i] = new Server(i + 1);
        }

        while (scanner.hasNextDouble()) {
            double arrivalTime = scanner.nextDouble();
            arrivalTimes.add(arrivalTime);
        }

        PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());

        int numOfCustomers = arrivalTimes.size();

        for (int i = 0; i < numOfCustomers; i++) {
            Customer newCustomer = new Customer(i + 1, arrivalTimes.get(i));
            events.add(new Event(States.ARRIVES, newCustomer, arrivalTimes.get(i)));

            // check if the waiters are busy.
            // if so see if we can free them
            for (int j = 0; j < numOfServers; j++) {
                Server waiter = servers[j];
                if (waiter.isBusy()) {
                    if (waiter.servingUntil() <= arrivalTimes.get(i)) {
                        // see if we can free the waiters
                        events.add(
                                new Event(States.DONE, waiter.servingWho(),
                                        waiter.servingUntil(), waiter)
                        );
                        waiter.free();
                        // after we free we check if we can serve the people waiting
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
                            if (waiter.servingUntil() <= arrivalTimes.get(i)) {
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
            boolean cannotBeServed = true;
            if (isAllBusy()) {
                for (int j = 0;  j < numOfServers; j++) {
                    Server waiter = servers[j];
                    if (!waiter.hasQ()) {
                        events.add(new Event(States.WAITS,newCustomer,arrivalTimes.get(i),waiter));
                        waiter.addQ(newCustomer);
                        cannotBeServed = false;
                        break;
                    }
                }
            } else {
                for (int j = 0; j < numOfServers; j++) {
                    Server waiter = servers[j];
                    if (!waiter.isBusy()) {
                        // if there are people queuing
                        waiter.serve(newCustomer,arrivalTimes.get(i));
                        totalCustomersServed++;
                        events.add(
                                new Event(States.SERVED, newCustomer, arrivalTimes.get(i),waiter)
                        );
                        cannotBeServed = false;
                        break;
                    }
                }
            }

            if (cannotBeServed) {
                events.add(new Event(States.LEAVES,newCustomer,arrivalTimes.get(i)));
            }
        }
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
