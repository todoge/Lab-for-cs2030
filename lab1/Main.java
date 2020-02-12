import java.util.Scanner;
class Main{
    public static Circle createCircle(Point p1, Point p2, double radius){
        Point mid = p1.midPoint(p2);
        double distance = Math.sqrt(Math.pow(radius,2) - Math.pow(p1.distanceTo(mid),2));
	if(Double.isNaN(distance) || p1.equals(p2)){
		return null;
	}
	else{
        	double angle = p1.angleTo(p2) + Math.PI/2;
        	Point centre =  mid.moveTo(angle,distance);
        	return  Circle.getCircle(centre,radius);
	}
    }
    private static int maxDC(Point[] arr) {
		int max = 0;
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			// check for valid circles
			Point curr_point = arr[i];
			for (int j = i+1; j < len; j++) {
				Point next = arr[j];
				if (curr_point.distanceTo(next) == 0 || curr_point.distanceTo(next) > 2) {
					continue;
				} else {
					Circle c = createCircle(curr_point, next, 1);
					int curr_max = 0;
					for (Point p : arr) {
						if (c.contains(p)) {
							curr_max++;
						}
					}
					if (curr_max > max) {
						max = curr_max;
					}
				}
			}
		}
		return max;
	}


		public static void main(String[] args){
			Scanner scanner = new Scanner(System.in);
			int numOfPoints = scanner.nextInt();
			Point[] arr = new Point[numOfPoints];
			for(int i = 0; i < numOfPoints; i++){

				double[] point_coord = new double[2];
				for(int k = 0; k < 2; k++){
					point_coord[k] = scanner.nextDouble();
				}
				Point temp = new Point(point_coord[0],point_coord[1]);
				arr[i] = temp;
			}
			int max = maxDC(arr);
			System.out.println("Maximum Disc Coverage: " + max);

    }

}
