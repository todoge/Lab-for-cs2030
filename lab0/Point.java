class Point{
    private double x;
    private double y;
    Point(double  x, double  y){
        this.x = x;
        this.y = y;
    }
    // get x coordinate
    public double getX(){
        return this.x;
    }

    // get y  coordinate
    public double getY(){
        return this.y;
    }
    // set x  coordinate
    public void setX(double _x){
        this.x = _x;
    }

    // set y coordinate
    public void setY(double _y){
        this.y = _y;
    }
 
    //mid point
    public Point midPoint(Point p){
        double _x = (this.x + p.x)/2;
        double _y = (this.y + p.y)/2;
        return new Point(_x,_y);
    }

    //atan2 between 2 points (angle to point B from current point)
    public double angleTo(Point p){
        return Math.atan2(this.x + p.x, this.y + p.y);
    }

    //returns a new point moved by a specified distance from current point
    public Point moveTo(double angle, double distance){
        double _x = this.x + (distance * Math.cos(angle));
        double _y = this.y + (distance * Math.sin(angle));
        return new Point(_x,_y);
    }
    //returns the distance to a specified point
    public double distanceTo(Point p){
	double _x = this.x - p.x;
	double _y = this.y - p.y;
	return Math.sqrt(Math.pow(_x,2)+Math.pow(_y,2));
    }
    @Override    
    // check if a point is equal to current point
    public boolean equals(Object obj){
	    if(this == obj){
		return true;
	    }
	    else if(obj instanceof Point){
		Point p = (Point) obj;
	    	return (this.x == p.x && this.y == p.y);
	    }
	    else{
		return false;
	    }
    }

    @Override
    public String toString(){
        return String.format("point (%.3f,%.3f)",this.x,this.y);
     }
}
