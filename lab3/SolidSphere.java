class SolidSphere extends Sphere{

	private final double density;

	SolidSphere(double radius, double density){
		super(radius);
		this.density = density;
	}

	public double getDensity(){
		return density;
	}

	public double getMass(){
		return density*this.getVolume();
	}

	@Override
	public SolidSphere setRadius(double _radius){
		return new SolidSphere(_radius,density);
	}

	@Override
	public String toString(){
		return "SolidSphere [" + String.format("%.2f", this.getRadius()) + "]" + " with a mass of " + String.format("%.2f",this.getMass());
	}
}


