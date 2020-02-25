class Server{
	private boolean isBusy = false;
	private Double servingSince;
	
	//constructor
	Server(){
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
	public double servingTo(){
		return servingSince + 1;
	}

	// frees the server
	public void free(){
		isBusy = false;
		servingSince = null;
	}

	// make the server serve from a given time
	public void serve(double time){
		servingSince = time;
		isBusy = true;
	}
}
		

	
