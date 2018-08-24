import java.util.LinkedList;
import java.util.Queue;

/**
 * Linear Probing Map
 *
 * @author Green, Acuna
 * @version 1.0
 */
public class LinearProbingMap<Key, Value> implements Map<Key,Value>{
    private class Entry<Key,Value> {
        public Key key;
        public Value value;
        public Entry (Key k, Value v) {
            key = k;
            value = v;
        }
    }

    private int N; // number of key-value pairs
    private int M; // hash table size

    private Key[] keys;
    private Value[] values;
    private Key Deleted = null;

    /**
     * Public constructor creating new ChainMap with Default size
     */
    public LinearProbingMap() {
        this(997);
    }


    /** Public constructor creating new ChainMap os size M
     * @param M size of new ChainMap
     */
    public LinearProbingMap(int M) {
        this.N = 0;
        this.M = M;
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    /**
     * Returns new hash code for input
     * @param key input to be hashed
     * @param i number of spaces to skip
     * @return new hash code
     */
    private int hash(Key key, int i) {
        return (key.hashCode() & 0x7fffffff + i) % M;
    }


    /**
     * Gets the value paired with a key. If the key doesn't exist in map,
     * returns null.
     * @param key Key to use.
     * @return Value associated with key.
     */
    @Override
    public Value get(Key key) {
        if(isEmpty())
            return null;
        for(int i = 0; i < M; i++){
            if(keys[hash(key,i)] == key)
                return values[hash(key,i)];
            if(keys[hash(key,i)] == null)
                return null;
        }
        return null;
    }

    /**
     * Puts a key-value pair into the map.
     *
     * @param key Key to use.
     * @param val Value to associate.
     */
    @Override
    public void put(Key key, Value val) {
        for(int i = 0; i < M; i++){
            if(keys[hash(key,i)] == key){
                values[hash(key,i)] = val;
                return;
            }
            if(keys[hash(key,i)] == null || keys[i] == Deleted)
            {
                keys[hash(key,i)] = key;
                values[hash(key,i)] = val;
                N++;
                return;
            }
        }
    }


    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return Number of key-value pairs.
     */
    @Override
    public int size() {
        return N;
    }

    /**
     * Returns true if there are no key-vale pairs stored in the map, and false
     * otherwise.
     *
     * @return True if map is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Checks if the map contains a particular key.
     *
     * @param k True if map contains key, false otherwise.
     * @return Result of check.
     */
    @Override
    public boolean contains(Key k) {
        return get(k) != null;
    }

    /**
     * Removes a key (and its associated value) from the map.
     *
     * @param key Key to use.
     */
    @Override
    public void remove(Key key) {
        if (contains(key)) {
            for (int i = 0; i < M; i++) {
                if (keys[hash(key, i)] == key)
                {
                    keys[hash(key, i)] = Deleted;
                    N--;
                    return;
                }
            }
        }
    }

    /**
     * Returns an Iterable object containing all keys in the table. Keys not
     * guaranteed to be in any particular order.
     *
     * @return Iterable object containing all keys.
     */
    @Override
    public Iterable<Key> keys() {
        if(isEmpty()){
            return null;
        }
        Queue<Key> queue = new LinkedList<>();
        for(Key key : keys)
            if(key != null)
                queue.add(key);
        return queue;
    }
}
