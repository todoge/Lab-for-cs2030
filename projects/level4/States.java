enum States{
	DONE(1),ARRIVES(2),WAITS(3),SERVED(4),LEAVES(5);
	private final int state;
	
	// private constructor to set value of state
	private States(int i){
		this.state = i;
	}

	public Integer getValue(){
		return this.state;
	}
	
	// override toString method
	@Override
	public String toString(){
		switch(state){
			case 1:
				return "done";
			case 2:
				return "arrives";
			case 3:
				return "waits";
			case 4:
				return "served";
			case 5:
				return "leaves";
			default:
				return "";
		}
	}

}
