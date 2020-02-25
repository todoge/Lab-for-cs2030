class Customer{
	private final int ID;
	private final double arrivalTime;
	private States state = null;

	// constructor
	Customer(int ID, double arrivalTime){
		this.ID = ID;
		this.arrivalTime = arrivalTime;
	}
	
	// gets customer ID
	public int getID(){
		return this.ID;
	}
	
	// gets arrival time of customer
	public double getArrivalTime(){
		return this.arrivalTime;
	}
	
	// set state of customer
	public void setState(States state){
		this.state = state;
	}
	
	// get current state of customer
	public States getState(){
		return this.state;
	}

	@Override 
	public String toString(){
		return ID + " arrives at " + String.format("%.3f",arrivalTime);
	}
}
