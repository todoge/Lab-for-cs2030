import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Function;
class ImmutableList<T>{
	private final List<T> lst;
	
	@SafeVarargs
	ImmutableList(T ...items){
		lst = new ArrayList<>();
		for(T item : items){
			lst.add(item);
		}
	}
	ImmutableList(List<T> items){
		List<T> temp = new ArrayList<>();
		for(T item : items){
			temp.add(item);
		}
		lst = temp;
	}
	
	//get method for ImmutableList
	public T get(int pos){
		return lst.get(pos);
	}

	// method to add item
	public ImmutableList<T> add(T item){
		List<T> temp = new ArrayList<>(lst);
		temp.add(item);
		return new ImmutableList<T>(temp);
	}

	// method to remove item
	public ImmutableList<T> remove(T item){
		List<T> temp = new ArrayList<>(lst);
		temp.remove(item);
		return new ImmutableList<T>(temp);
	}
	
	// method to replace item
	public ImmutableList<T> replace(T itemToReplace, T newItem){
		List<T> temp = new ArrayList<>();
		for(T item : lst){
			if(item.equals(itemToReplace)){
				temp.add(newItem);
			}
			else{
				temp.add(item);
			}
		}
		return new ImmutableList<T>(temp);
	}

	
	// method to set item
	public ImmutableList<T> set(int pos, T item){
		List<T> temp = new ArrayList<>(lst);
		temp.set(pos,item);
		return new ImmutableList<T>(temp);
	}

	// filter method to filter items that passes the  for ImmutableList
	public ImmutableList<T> filter(Predicate<T> func){
		List<T> temp = new ArrayList<>();
		for(T item : lst){
			if(func.test(item)){
				temp.add(item);
			}
		}
		return new ImmutableList<T>(temp);
	}
	
	// map method to map function to every element
	public <E> ImmutableList<E> map(Function<? super T,? extends E> func){
		List<E> temp = new ArrayList<>();
		for(T item : lst){
			temp.add(func.apply(item));
		
		}
		return new ImmutableList<E>(temp);
	}

	@Override
	public String toString(){
		return Arrays.toString(lst.toArray());
	}

}
	
