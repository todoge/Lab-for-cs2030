class LastDigitsOfHashCode implements Transformer<Object ,Integer>{
	
	private final Integer digits;

	LastDigitsOfHashCode(Integer num){
		digits = num;
	}


	//transform a box into an integer
	@Override
	public Integer transform(Object item){
		return Math.abs(item.hashCode()) % (int)Math.pow(10,digits);
	}


}
