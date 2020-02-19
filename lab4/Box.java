@SuppressWarnings("unchecked")
class Box <T> {
	private final T item;
	private static final Box<?> EMPTY_BOX = new Box(null); 	
	
	// private constructor for Box
	private Box(T item){
		this.item = item;
	}
	
	// creates a Box of specified item
	public static <T> Box<T> of(T item){
		if(item == null){
			return null;
		}
		else {
			return new Box<>(item);
		}
	}
	
	// get item in Box
	public T get(){
		return item;
	}
	
	//returns an empty Box
	static <T> Box<T> empty(){
		return (Box<T>) EMPTY_BOX;
	}
	
	// returns true if something is present else false
	public boolean isPresent(){
		return !this.equals(empty());
	}
	
	// behaves just like of if the input is non-null, and returns an empty box if the input is null
	public static <T> Box<T> ofNullable(T item){
		if(item == null){
			return empty();
		}
		else{
			return of(item);
		}
	}

	//filter returns empty box if item in box fail the test
	//else filter returns the box as it is
	public Box filter(BooleanCondition<T> tester){
		if(tester.test(item)){
			return this;
		}
		else{
			return empty();
		}
	}
	
	// tranform current box type to another box type
	public Box map(Transformer transformer){
		if(this.item == null){
			return empty();
		}
		else{
			return new Box<>(transformer.transform(this.item));
		}
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof Box){
			if(this == obj){
				return true;
			}
			else{
				return ((Box) obj).item == this.item;
			}

		}
		else{
			return false;
		}
	}

	@Override
	public String toString(){
		if(item == null){
			return "[]";
		}
		else{
			return "[" + item + "]";
		}
	}
}

