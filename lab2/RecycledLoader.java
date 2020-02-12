class RecycledLoader extends Loader{
	RecycledLoader(int id){
		super(id);
	}
	RecycledLoader(int loaderID, Cruise serving){
		super(loaderID,serving);
	}
	@Override
	public boolean canServe(Cruise ship){
		if(serving==null){
			return true;
		}
		else if(ship.getArrivalTime() >= serving.getServiceCompletionTime()+60){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Loader serve(Cruise ship){
		if(this.canServe(ship) == false){
			return null;
		}
		else if(ship == null){
			return new RecycledLoader(this.loaderID);
		}
		else{
			return new RecycledLoader(this.loaderID,ship);
		}
	}

	@Override
    	public String toString(){
	    if(serving == null){
	    	return "Loader " + loaderID;
	    }
	    else{
		return ("Loader " + this.loaderID + " (recycled) serving " + serving);
	    }

    	}
}
