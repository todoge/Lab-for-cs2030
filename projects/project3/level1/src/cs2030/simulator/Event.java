package cs2030.simulator;
/**
 * This is a class to create an Event.
 *
 * @author peh jun siang
 */
public class Event {
    private final States state;
    private final Customer customer;
    private final Double time;
    private Server server;
    /**
     * Event Constructor to create an event.
     *
     * @param state    is the State of the customer
     * @param customer is the current customer in the event
     * @param time     is the time the event is currently happening
     * @author peh jun siang
     * @see States
     * @see Customer
     */

    Event(States state, Customer customer, Double time) {
        this.state = state;
        this.customer = customer;
        this.time = time;
    }

    /**
     * Overloaded Event Constructor to create an event with server.
     *
     * @param state    is the State of the customer
     * @param customer is the current customer in the event
     * @param time     is the time the event is currently happening
     * @param server is the server serving the customer
     * @author peh jun siang
     * @see States
     * @see Customer
     */
    Event(States state, Customer customer, Double time, Server server) {
        this.state = state;
        this.customer = customer;
        this.time = time;
        this.server = server;
    }

    /**
     * getState returns the state of the Event.
     *
     * @return state
     * @author peh jun siang
     * @see #state
     */
    public States getState() {
        return state;
    }

    /**
     * getCustomer returns the customer in the event.
     *
     * @return customer
     * @author peh jun siang
     * @see #customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * getTime returns the Time of the event.
     *
     * @return time
     * @author peh jun siang
     * @see #time
     */
    public Double getTime() {
        return time;
    }

    /**
     * Overrides toString method to the stipulated format.
     *
     * @return String
     * @author peh jun siang
     * @see #toString()
     */
    @Override
    public String toString() {
        if (state == States.DONE) {
            return String.format("%.3f", time) + " " + customer.getId() + " " + state
                    + " serving by " + server.getId();
        } else if (state == States.SERVED) {
            return String.format("%.3f", time) + " " + customer.getId() + " " + state + " by "
                    + server.getId();
        } else if (state == States.WAITS) {
            return String.format("%.3f", time) + " " + customer.getId() + " " + state
                    + " to be served by " + server.getId();
        } else {
            return String.format("%.3f", time) + " " + customer.getId() + " " + state;
        }
    }
}
