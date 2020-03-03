/**
 * This is a model class to for the Customer.
 *
 *  @author peh jun siang
 */
class Customer {
    private final int id;
    private final double arrivalTime;

    // constructor
    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    /**
     * getID retrieves the ID of the Customer.
     *
     * @author peh jun siang
     */
    // gets customer ID
    public int getId() {
        return this.id;
    }

    /**
     * getArrivalTime retrieves the arrivalTime of the customer.
     *
     * @author peh jun siang
     */
    // gets arrival time of customer
    public double getArrivalTime() {
        return this.arrivalTime;
    }

    @Override
    public String toString() {
        return id + " arrives at " + String.format("%.3f",arrivalTime);
    }
}
