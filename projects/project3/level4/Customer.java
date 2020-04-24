package cs2030.simulator;
/**
 * This is a model class to for the Customer.
 *
 *  @author peh jun siang
 */
public class Customer {
    private final Integer id;
    private final Double arrivalTime;
    private Double serviceTimeRequired = null;
    private boolean isGreedy = false;

    // constructor
    Customer(int id, double arrivalTime, boolean isGreedy) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTimeRequired = serviceTimeRequired;
        this.isGreedy = isGreedy;
    }

    /**
     * getID retrieves the ID of the Customer.
     *
     * @return  id (int).
     * @author peh jun siang.
     */
    // gets customer ID
    public Integer getId() {
        return this.id;
    }

    /**
     * getArrivalTime retrieves the arrivalTime of the customer.
     *
     * @return arrivalTime (double).
     * @author peh jun siang
     */
    // gets arrival time of customer
    public Double getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * getServiceTime retrieves the service time required for the customer.
     *
     * @return service time (double).
     * @author peh jun siang
     */
    // gets service time of customer
    public Double getServiceTime() {
        return this.serviceTimeRequired;
    }


    /**
     * setServiceTime set the service time.
     *
     * @author peh jun siang
     */
    // sets service time of customer
    public void setServiceTime(double serviceTimeRequired) {
        this.serviceTimeRequired = serviceTimeRequired;
    }

    /**
     * returns true if customer is greedy
     *
     * @return boolean
     * @author peh jun siang
     */
    public boolean isGreedy() {
        return isGreedy;
    }

    /**
     * Overrides toString method to the stipulated format.
     *
     * @return String
     * @author peh jun siang
     * @see #toString()
     */
    @Override
    public String toString(){
        if (isGreedy){
            return id + "(greedy)";
        } else {
            return id + "";
        }
    }
}
