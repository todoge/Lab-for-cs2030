class SolidCuboid extends Cuboid{
	private double density;

	SolidCuboid(double h, double w, double l, double density){
		super(h,w,l);
		this.density = density;
	}

	public double getDensity(){
		return density;
	}

	public double getMass(){
		return density * this.getVolume();
	}
	@Override
	public SolidCuboid setWidth(double _width){
		return new SolidCuboid(this.getHeight(),_width,this.getLength(), density);
	}
	@Override
	public SolidCuboid setHeight(double _height){
		return new SolidCuboid(_height,this.getWidth(),this.getLength(), density);
	}
	@Override
	public SolidCuboid setLength(double _length){
		return new SolidCuboid(this.getHeight(),this.getWidth(),_length, density);
	}

	@Override
	public String toString(){
		return "SolidCuboid [" + String.format("%.2f",this.getHeight()) + " x " +  String.format("%.2f",this.getWidth()) + " x " +  String.format("%.2f",this.getLength()) + "]"
			+ " with a mass of " + String.format("%.2f",this.getMass());
	}
}

