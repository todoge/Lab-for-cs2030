/**
 * Server class to create a waiter.
 *
 * @author peh jun siang.
 */
public class Server {
    private boolean isBusy;
    private Double servingSince;
    private Customer serving;
    private static final int timeNeededToServe = 1;

    /**
     * Server Constructor.
     *
     * @author peh jun siang.
     */
    //constructor
    Server() {
        isBusy = false;
        servingSince = null;
        serving = null;
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
    public double servingSince() {
        return servingSince;
    }

    /**
     * servingUntil returns the time the server will be serving Until.
     *
     * @return servingSince + timeNeededToServe (double).
     * @author peh jun siang.
     */
    // returns the time the server will be serving until
    public double servingUntil() {
        return servingSince + timeNeededToServe;
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
     * @param time (double).
     * @author peh jun siang.
     */
    // make the server serve a customer from a given time
    public void serve(Customer customer, double time) {
        servingSince = time;
        isBusy = true;
        serving = customer;
    }

    /**
     * getServing returns the customer server is currently serving.
     *
     * @return customer
     * @author peh jun siang
     */
    // get the customer the server is currently serving
    public Customer getServing() {
        return serving;
    }


}



