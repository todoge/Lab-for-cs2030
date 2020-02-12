class Cuboid implements Shape3D{
	private final double length;
	private final double width;
	private final double height;

	Cuboid(double height, double width, double length){
		this.height = height;
		this.length = length;
		this.width = width;
	}
	
	public Cuboid setWidth(double _width){
		return new Cuboid(height,_width,length);
	}

	public Cuboid setHeight(double _height){
		return new Cuboid(_height,width,length);
	}
	public Cuboid setLength(double _length){
		return new Cuboid(height,width,_length);
	}


	public double getHeight(){
		return height;
	}

	public double getWidth(){
		return width;
	}
	
	public double getLength(){
		return length;
	}
	@Override
	public double getSurfaceArea(){
		double side1 = width * length;
		double side2 = width * height;
		double side3 = length * height;
		return (side1 + side2 + side3) * 2;
	}
	@Override
	public double getVolume(){
		return height * length * width;
	}
	
	@Override
	public String toString(){
		return "Cuboid [" + String.format("%.2f",height) + " x " +  String.format("%.2f",width) + " x " +  String.format("%.2f",length) + "]";  

	}
}

