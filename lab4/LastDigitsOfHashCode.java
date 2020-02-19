class LastDigitsOfHashCode implements Transformer<Object,Integer>{
	
	private final Integer digits;

	LastDigitsOfHashCode(int num){
		digits = num;
	}


	//transform a box into an integer
	@Override
	public Integer transform(Object item){
		return item.hashCode() % (int)Math.pow(10,digits);
	}


}
