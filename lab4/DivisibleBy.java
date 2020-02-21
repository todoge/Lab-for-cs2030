class DivisibleBy implements BooleanCondition<Integer>{
	private final Integer number;

	DivisibleBy(Integer num){
		number = num;
	}

	@Override
	public boolean test(Integer num2){
		if(num2 == null){
			return false;
		}	
		else{
			return (int) num2 % (int) number == 0;
		}
	}
}
