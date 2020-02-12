import java.util.Scanner;
import java.util.StringTokenizer;
class Main6{

	private static void assignLoaderToCruise(Cruise[] cruises, int numOfLoaders){

		Loader[] loaders = new Loader[numOfLoaders];
		// create loaders
		for(int i = 0; i < numOfLoaders; i++){
			if((i+1) % 3 == 0){
				loaders[i] = new RecycledLoader(i+1);
			}
			else{
				loaders[i] = new Loader(i+1);
			}
		}

		for(Cruise ship : cruises){
			int numOfloadersNeeded = ship.getNumOfLoadersRequired();
			for(int i = 0; i < numOfLoaders; i++){
				if(numOfloadersNeeded == 0){
					break;
				}
				else if(loaders[i].canServe(ship)){
					loaders[i] = loaders[i].serve(ship);
					numOfloadersNeeded--;
					System.out.println(loaders[i].toString());
				}
			}
		}
	}


	public static void main(String[] arg){

		Scanner scanner = new Scanner(System.in);
		int numberOfShips = Integer.parseInt(scanner.nextLine());

		Cruise[] cruises = new Cruise[numberOfShips];
		// handle user input
		for(int i = 0; i < numberOfShips; i++){
			// scans until enter is pressed
			String[] input = scanner.nextLine().split("\\s");

			if(input.length == 4){
				String id = input[0];
				int time = Integer.parseInt(input[1]);
				int length = Integer.parseInt(input[2]);
				int passengers = Integer.parseInt(input[3]);
				cruises[i] = new BigCruise(id,time,length,passengers);
			} else if(input.length == 2){
				String id = input[0];
				int time = Integer.parseInt(input[1]);
				cruises[i] = new SmallCruise(id,time);
			}
		}

		assignLoaderToCruise(cruises,9*numberOfShips);
	}
}
