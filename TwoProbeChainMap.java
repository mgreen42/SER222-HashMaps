import java.util.LinkedList;
import java.util.Queue;

/**
 * Two Probe Chain Map
 *
 * @author Green, Acuna
 * @version 1.0
 */
public class TwoProbeChainMap<Key,Value> implements Map<Key,Value> {

    //Size of ChainMap
    int M;
    // Number of pairs of key/values
    int N;
    //Entry[] entries;
    private LinkedList<Entry>[] entries;

    private static final int DEFAULT_CAPACITY = 997;

    public class Entry<K,V> {

        private Object key;
        private Object value;

        public Entry(Object key, Object value){
            this.key = key;
            this.value = value;
        }

    }

    /**
     * Public constructor creating new ChainMap with Default size
     */
    public TwoProbeChainMap(){
        this(DEFAULT_CAPACITY);
    }

    /**
     * Public constructor creating new ChainMap os size M
     * @param M size of new ChainMap
     */
    public TwoProbeChainMap(int M){
        this.N = 0;
        this.M = M;
        entries = new LinkedList[M];
        for (int i = 0; i < M; i++)
            entries[i] = new LinkedList<>();
    }

    public int getHash(Key key){
        return (Math.min(entries[hash(key)].size(), entries[hash2(key)].size()));
    }

    /**
     * Returns new hash code for input
     * @param key input to be hashed
     * @return new hash code
     */
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * Returns second type of hash code for input
     * @param key input to be hashed
     * @return new hash code
     */
    private int hash2(Key key){
        return (((key.hashCode() & 0x7fffffff) % M) * 31) % M;
    }

    /**
     * Puts a key-value pair into the map.
     *
     * @param key Key to use.
     * @param val Value to associate.
     */
    public void put(Key key, Value val){
        if(key == null)
            return;

        boolean added = false;

        Key key2 = (Key) key;
        int hash = getHash(key2);

        for(Entry entry : entries[hash])
            if(entry != null)
                if(key.hashCode() == entry.key.hashCode()) {
                    entry.value = val;
                    added = true;
                }


        if(!added) {
            entries[hash].add(new Entry(key, val));
            N++;
        }

    }

    /**
     * Gets the value paired with a key. If the key doesn't exist in map,
     * returns null.
     * @param key Key to use.
     * @return Value associated with key.
     */
    public Value get(Key key){

        if(key == null){
            return null;
        }

        int hash1 = getHash(key);
        for(Entry e : entries[hash1]){
            if(key == e.key)
                return (Value) e.value;
        }

        for(Entry e : entries[hash2(key)]){
            if(key == e.key)
                return (Value) e.value;
        }

        return null;

    }

    /**
     * Removes a key (and its associated value) from the map.
     *
     * @param key Key to use.
     */
    public void remove(Key key){
        if(key == null)
            return;


        if(contains(key)) {
            Entry target = null;
            int hash1 = getHash(key);
            for(Entry e : entries[hash1]){
                if(e.key == key){
                    target = e;
                    entries[hash1].remove(target);
                    N--;
                    return;
                }
            }
            int hash2 = hash2(key);
            for(Entry e : entries[hash2]){
                if(e.key == key){
                    target = e;
                    entries[hash2].remove(target);
                    N--;
                }
            }
        }
    }

    /**
     * Checks if the map contains a particular key.
     *
     * @param key True if map contains key, false otherwise.
     * @return Result of check.
     */
    @Override
    public boolean contains(Key key){return get(key) != null;}

    /**
     * Returns true if there are no key-vale pairs stored in the map, and false
     * otherwise.
     *
     * @return True if map is empty, false otherwise.
     */
    public boolean isEmpty(){ return(N==0); }

    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return Number of key-value pairs.
     */
    public int size(){
        return N;
    }

    /**
     * Returns an Iterable object containing all keys in the table. Keys not
     * guaranteed to be in any particular order.
     *
     * @return Iterable object containing all keys.
     */
    public Iterable<Key> keys(){
        Queue<Key> queue = new LinkedList<>();

        for (int i = 0; i < M; i++)
            for(Entry e : entries[i])
                queue.add((Key)e.key);

        return queue;

    }



}
