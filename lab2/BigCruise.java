class BigCruise extends Cruise{

	BigCruise(String id, int arrivalTime, double length, double numOfPassengers){
		super(id,arrivalTime, (int) Math.ceil(length/40) , (int) Math.ceil(numOfPassengers/50));
	}
}
