class SolidShape3D implements Shape3D{ 
	private Shape3D shape;
	private Material material;

	SolidShape3D( Shape3D shape, Material material){
		this.shape = shape;
		this.material = material;
	}
	
	@Override
	public double getVolume(){
		return shape.getVolume();
	}

	@Override
	public double getSurfaceArea(){
		return shape.getSurfaceArea();
	}

	public double getMass(){
		return shape.getVolume() * material.getDensity();
	}

	public double getDensity(){
		return material.getDensity();
	}

	@Override
	public String toString(){
		return "Solid" + shape.toString() + " with a mass of " + String.format("%.2f",this.getMass());
	}
}

