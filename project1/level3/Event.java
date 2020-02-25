class Event{
	private final States state;
	private final Customer customer;
	private final double time;
	
	Event(States state, Customer customer, double time){
		this.state = state;
		this.customer = customer;
		this.time = time;
	}

	public States getState(){
		return state;
	}

	public Customer getCustomer(){
		return customer;
	}

	public double getTime(){
		return time;
	}
	
	@Override
	public String toString(){
		return String.format("%.3f",time) + " " + customer.getID() + " " + state; 
	}
}
