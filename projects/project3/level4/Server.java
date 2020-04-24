package cs2030.simulator;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Server class to create a waiter.
 *
 * @author peh jun siang.
 */
public class Server {
    private int id;
    private boolean isBusy;
    private Double servingSince;
    private Customer serving;
    private Double servingUntil;
    private PriorityQueue<Customer> waitingQ;
    private int maxQueueLen;
    private ServerStates state;

    /**
     * Server Constructor.
     *
     * @param id (int)
     * @param maxQueueLen (int)
     * @author peh jun siang.
     */
    //constructor
    Server(int id, int maxQueueLen) {
        this.id = id;
        isBusy = false;
        servingSince = null;
        serving = null;
        waitingQ = new PriorityQueue<>((c1,c2) ->
                c1.getArrivalTime().compareTo(c2.getArrivalTime())
        );
        state = ServerStates.SERVER_BACK;
        this.maxQueueLen = maxQueueLen;
    }

    /**
     * getId returns the id of the server.
     *
     * @return id (int).
     * @author peh jun siang.
     */
    // returns true if server is busy
    public Integer getId() {
        return id;
    }

    /**
     * popQ returns the Customer currently waiting for server and removes it from the Q.
     *
     * @return Customer (customer).
     * @author peh jun siang.
     */
    public Customer popQ() {
        return waitingQ.poll();
    }

    /**
     * Adds a customer to the Q. Returns true if customer can be added.
     *
     * @param customer (takes a customer to add to the queue)
     * @return Customer (customer).
     * @author peh jun siang.
     */
    public boolean addQ(Customer customer) {
        return waitingQ.add(customer);
    }

    /**
     * peek waitingQ.
     *
     * @return Customer (customer).
     * @author peh jun siang.
     */
    public Customer peekQ() {
        return waitingQ.peek();
    }

    /**
     * Returns true if theres Customer is waiting for server.
     *
     * @return boolean
     * @author peh jun siang.
     */
    public boolean hasQ() {
        return !waitingQ.isEmpty();
    }

    /**
     * isBusy method returns true if server is busy.
     *
     * @return isBusy (boolean).
     * @author peh jun siang.
     */
    // returns true if server is busy
    public boolean isBusy() {
        return isBusy;
    }

    /**
     * servingSince returns the time the server started serving.
     *
     * @return servingSince (double).
     * @author peh jun siang.
     */
    // returns the time the server begun serving
    public Double servingSince() {
        return servingSince;
    }

    /**
     * servingUntil returns the time the server will be serving Until.
     *
     * @return servingSince + timeNeededToServe (double).
     * @author peh jun siang.
     */
    // returns the time the server will be serving until
    public Double servingUntil() {
        return servingUntil;
    }

    /**
     * frees the server.
     *
     * @author peh jun siang.
     */
    // frees the server but keeps last time server was last busy
    public void free() {
        isBusy = false;
        serving = null;
    }

    /**
     * serve makes the server serve a customer from a given time and sets server to busy.
     *
     * @param customer Takes in a customer.
     * @see Customer
     * @param arrivalTime (double).
     * @author peh jun siang.
     */
    // make the server serve a customer from a given time
    public void serve(Customer customer, double arrivalTime) {
        servingSince = arrivalTime;
        servingUntil = arrivalTime + customer.getServiceTime();
        isBusy = true;
        serving = customer;
        state = ServerStates.SERVER_BACK;
    }

    /**
     * servingWho returns the customer server is currently serving.
     *
     * @return customer
     * @author peh jun siang
     */
    // get the customer the server is currently serving.
    public Customer servingWho() {
        return serving;
    }

    /**
     * canQ returns whether a customer can queue for the server.
     *
     * @return boolean
     * @author peh jun siang
     */
    public boolean canQ() {
        return waitingQ.size() < maxQueueLen;
    }

    /**
     * returns whether a server can serve or otherwise.
     *
     * @return boolean
     * @author peh jun siang
     */
    // returns true if server can serve
    public boolean canServe() {
        return (state == ServerStates.SERVER_BACK) && !isBusy;
    }

    /**
     * set server as resting.
     *
     * @author peh jun siang
     */
    // returns true if server can serve
    public void rest(double restingTime) {
        servingSince = servingUntil;
        servingUntil += restingTime;
        state = ServerStates.SERVER_REST;
    }

    /**
     * set server as back.
     *
     * @author peh jun siang
     */
    // returns true if server can serve
    public void back() {
        state = ServerStates.SERVER_BACK;
    }

    /**
     * check if server is back.
     *
     * @return boolean
     * @author peh jun siang
     */
    // returns true if server is back
    public boolean isBack() {
        return state == ServerStates.SERVER_BACK;
    }

    /**
     * check if server is a self check out station.
     *
     * @return boolean
     * @author peh jun siang
     */
    // returns true if server is a self checkout station
    public boolean isSelfCheckOut() {
        return false;
    }

    /**
     *  returns the length of the queue.
     *
     * @return length of queue (Integer).
     * @author peh jun siang.
     */
    // returns length of queue
    public Integer waitingQLength() {
        return waitingQ.size();
    }
}



