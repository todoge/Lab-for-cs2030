class SmallCruise extends Cruise{
	private static final int numOfLoaders = 1;
	private static final int serviceTime = 30;
	SmallCruise(String id, int time){
		super(id,time,numOfLoaders,serviceTime);
	}
}
