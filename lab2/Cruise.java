class Cruise{
    protected final int arrivalTime;
    protected final String id;
    protected final int loaders;
    protected final int serviceTime;

    Cruise(String id,int arrivalTime, int loaders, int serviceTime){
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.loaders = loaders;
        this.serviceTime = serviceTime; 
    }

    public int getArrivalTime(){
	int time = arrivalTime;
        int mins = time%100;
        int hour = time/100;
        return hour*60 + mins;
    }

    public int getNumOfLoadersRequired(){
        return loaders;
    }

    public int getServiceCompletionTime(){
        return serviceTime + this.getArrivalTime();
    }

    public Cruise setLoader(int _loaders){
        return new Cruise(id,arrivalTime,_loaders,serviceTime);
    }
    @Override
    public String toString(){
        return id + "@" + String.format("%04d",arrivalTime);
    }
}
