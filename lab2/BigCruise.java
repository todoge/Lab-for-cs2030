class BigCruise extends Cruise{
	
	private final static int lengthPerLoader = 40;
	private final static int passengerPerMinute = 50;
	BigCruise(String id, int arrivalTime, double length, double numOfPassengers){
		super(id,arrivalTime, (int) Math.ceil(length/lengthPerLoader) , (int) Math.ceil(numOfPassengers/passengerPerMinute));
	}
}
