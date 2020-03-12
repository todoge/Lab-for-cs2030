import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

class KeyableMap<T,K,V extends Keyable<K>> implements Keyable<T>{
    
    public Map<K,V> map;
    public T mapType;

    KeyableMap(T mapType){
        this.mapType = mapType;
        this.map = new HashMap<K,V>();
    }

    //put method for keyable map
    public KeyableMap<T,K,V> put(V item){
        map.put(item.getKey(), item);
        return this;
    }

    // returns an Optional object
    public Optional<V> get(K searchKey){
        return Optional.ofNullable(map.get(searchKey));
    }

    @Override
    public T getKey(){
        return mapType;
    }

    @Override 
    public String toString() {
        String items =  mapType.toString() + ": {";
        int count = 0;
        for (V item : map.values()){
            count++;
            items += item;        
            if(count < map.size()){
                items += ", ";
            }
        }
        return items + "}"; 
    }
}

