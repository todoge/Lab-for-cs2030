class Loader{
    protected int loaderID;
    protected Cruise serving;

    Loader(int loaderID){
        this.loaderID = loaderID;
    }
    
    // overload constructor
    protected Loader(int loaderID, Cruise serving){
	    this.loaderID = loaderID;
	    this.serving = serving;
    }

    public boolean canServe(Cruise ship){
	if(serving == null){
		return true;
	}
	else if(ship.getArrivalTime() >= serving.getServiceCompletionTime()){
		return true;
	}
	else{
		return false;
	}
    }

    public Loader serve(Cruise ship){
    	if(this.canServe(ship) == false){
		return null;
	}
    	else if(ship == null){
		return new Loader(this.loaderID);
	}
	else{
		return new Loader(this.loaderID,ship);
	}
    }

    @Override
    public String toString(){
	    if(serving == null){
	    	return "Loader " + loaderID;
	    }
	    else{
		return ("Loader " + this.loaderID + " serving " + serving);
	    }

    }
}


