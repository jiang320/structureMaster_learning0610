package mywork;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash <T>{
    private final Hashing hash;
    private final int numberOfReplicas;
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();

    public ConsistentHash(Hashing hash, int numberOfReplicas,
                           Collection<T> nodes) {
        super();
        this.hash = hash;
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
    }

    private void add(T node) {
        for(int i=0;i<this.numberOfReplicas;i++){
            circle.put(this.hash.hash(node.toString()+i),node);
        }
    }
    public void remove(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.remove(this.hash.hash(node.toString() + i));
        }
    }
    public T get(String key){
        if(circle.isEmpty()){
            return null;
        }
        long hash=this.hash.hash(key);
        //if(!circle.containsKey(key)){
        if(!circle.containsKey(hash)){
            SortedMap<Long,T> tailMap =circle.tailMap(hash);
            hash =tailMap.isEmpty()?circle.firstKey():tailMap.firstKey();

        }
        return circle.get(hash);
    }

}
