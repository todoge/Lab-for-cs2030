class Circle{
    private double radius;
    private Point centre;
    private Circle(Point centre, double radius){
        this.centre = centre;
        this.radius = radius;
    }
    //get radius
    public double getRadius(){
	    return this.radius;
    }

    //get centre
    public Point getCentre(){
	    return this.centre;
    }
    // check if a point is contain within a circle
    public boolean contains(Point p){
	return this.centre.distanceTo(p) <= radius;
    }

    //factory method
    public static Circle getCircle(Point c, double r){
        if(r  > 0){
            return new Circle(c,r);
        }
        else{
            return null;
        }
    }

    //override string method
    public String toString(){
        return String.format("circle of radius %.1f centered at point (%.3f, %.3f)",this.radius,
                                this.centre.getX(),this.centre.getY());
    }
}    

