/**
 * This is a model class to for the Customer
 *
 *  @author peh jun siang
 */
class Customer{
	private final int ID;
	private final double arrivalTime;

	// constructor
	Customer(int ID, double arrivalTime){
		this.ID = ID;
		this.arrivalTime = arrivalTime;
	}
	/**
	 * getID retrieves the ID of the Customer
	 *
	 * @author peh jun siang
	 */
	// gets customer ID
	public int getID(){
		return this.ID;
	}

	/**
	 * getArrivalTime retrieves the arrivalTime of the customer
	 *
	 * @author peh jun siang
	 */
	// gets arrival time of customer
	public double getArrivalTime(){
		return this.arrivalTime;
	}

	@Override 
	public String toString(){
		return ID + " arrives at " + String.format("%.3f",arrivalTime);
	}
}
