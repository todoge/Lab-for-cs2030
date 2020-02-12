import java.util.Scanner;
class Main{
    public static Circle createCircle(Point p1, Point p2, double radius){
        Point mid = p1.midPoint(p2);
        double distance = Math.sqrt(Math.pow(radius,2) - Math.pow(p1.distanceTo(mid),2));
	if(Double.isNaN(distance) || p1.equals(p2)){
		return null;
	}
	else{
        double angle = p1.angleTo(p2);
        Point centre =  mid.moveTo(angle,distance);
        return  Circle.getCircle(centre,radius);
	}
    }

    public static void main(String[] args){
	    Scanner scanner = new Scanner(System.in);
	    double[] arr = new double[5];
	    for(int i = 0; i < 5; i ++){
		arr[i] = scanner.nextDouble();
	    }
	    Point p1 = new Point(arr[0],arr[1]);
	    Point p2 = new Point(arr[2],arr[3]);
	    Circle c = createCircle(p1,p2,arr[4]);
	    if(c != null){
		    System.out.println(String.format("Created: circle of radius %.3f centred at point (%.3f,%.3f)",c.getRadius(),
                                c.getCentre().getX(),c.getCentre().getY()));
	    }
	    else{
		    System.out.println("No valid circle can be created");
	    }

    }

}
