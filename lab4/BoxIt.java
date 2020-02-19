class BoxIt<T> implements Transformer<Object,Box>{
	
	@Override
	public Box transform(Object item){
		return Box<T>.of(Box.of(item));
	}
}
