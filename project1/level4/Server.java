class Server{
	private boolean isBusy;
	private Double servingSince;
	private Customer serving; 

	//constructor
	Server(){
		isBusy = false;
		servingSince = null;
		serving = null;
	}
	
	// returns true if server is busy
	public boolean isBusy(){
		return isBusy;
	}

	// returns the time the server begun serving
	public double servingSince(){
		return servingSince;
	}

	// returns the time the server will be serving until
	public double servingUntil(){
		return servingSince + 1;
	}

	// frees the server
	public void free(){
		isBusy = false;
		servingSince = null;
		serving = null;
	}

	// make the server serve a customer from a given time
	public void serve(Customer customer, double time){
		servingSince = time;
		isBusy = true;
		serving = customer;
	}

	// get the customer the server is currently serving
	public Customer getServing(){
		return serving;
	}


}
		

	
