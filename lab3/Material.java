class Material{
	private String name;
	private double density;

	Material(String name, double density){
		this.name = name;
		this.density = density;
	}

	public double getDensity(){
		return density;
	}

	public String getName(){
		return name;
	}
}
