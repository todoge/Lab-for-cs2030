class Customer{
	private final int ID;
	private final double arrivalTime;
	
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

	@Override 
	public String toString(){
		return ID + " arrives at " + String.format("%.3f",arrivalTime);
	}
}
