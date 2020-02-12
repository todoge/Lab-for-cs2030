class Sphere implements Shape3D{
	private final double radius;
	Sphere(double radius){
		this.radius = radius;
	}
	
	public Sphere setRadius(double _radius){
		return new Sphere(_radius);
	}

	public double getRadius(){
		return this.radius;
	}

	@Override
	public double getVolume(){
		return ((4.0/3.0) * Math.PI * Math.pow(radius,3));
	}

	@Override
	public double getSurfaceArea(){
		return 4.0*Math.PI*Math.pow(radius,2);
	}

	@Override
	public String toString(){
		return "Sphere [" + String.format("%.2f", radius) + "]";
	}

}
