class BoxIt <T> implements Transformer<T, Box<T>>{
	
	@Override
	public Box<T> transform(T item){
		return Box.of(item);
	}
}
