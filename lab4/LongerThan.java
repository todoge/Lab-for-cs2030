class LongerThan implements BooleanCondition<String>{

	private final int length;
	
	public LongerThan(int length){
		this.length = length;
	}

	@Override
	public boolean test(String s){
		return s.length() > length;
	}
}
		
