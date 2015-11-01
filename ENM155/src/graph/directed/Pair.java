package graph.directed;

/** Java representation of Tuples (Pairs). */
public class Pair<K, V> {

	/** First part of the pair. */
    private K key;
    
    /** Second part of the pair. */
    private V value;

    /** Create a new (key, value) pair. */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    // ------------------------------------------------------------------------
    // Getters & Setters

    /** Sets the first member of the pair. */
    public void setKey(K key) { 
    	this.key = key; 
    }
    
    /** Sets the second member of the pair. */
    public void setValue(V value) { 
    	this.value = value; 
    }
    
    /** Fetches the first member of the pair. */
    public K getKey() { 
    	return key; 
    }
    
    /** Fetches the second member of the pair. */
    public V getValue() { 
    	return value; 
    }
}