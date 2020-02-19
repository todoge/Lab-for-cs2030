class AlwaysTrue<T> implements BooleanCondition<T> {
	public boolean test(T t) { return true; } 
}
