enum States{
	ARRIVES(1),SERVED(2),LEAVES(3);
	private final int state;
	
	// private constructor to set value of state
	private States(int i){
		this.state = i;
	}

	public int getValue(){
		return this.state;
	}
	
	// override toString method
	@Override
	public String toString(){
		switch(state){
			case 1:
				return "arrives";
			case 2:
				return "serves";
			case 3:
				return "leaves";
			default:
				return "";
		}
	}

}
