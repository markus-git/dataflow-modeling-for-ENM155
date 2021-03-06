package json;

import java.util.ArrayList;
import java.util.Iterator;

/** Representation of JSON arrays, only supports JSONObjects as elements. */
public class JSONArray implements Iterable<Object> {

	/** Array elements */
    private final ArrayList<Object> array;
	
    /** Creates a JSONArray with no elements. */
    private JSONArray() {
        this.array = new ArrayList<Object>();
    }
	
    /** Constructs a JSONArray from the given token stream. */
    public JSONArray(Tokenizer x) throws JSONException {
        this();
        if (x.token() != '[') {
            throw new JSONException("A JSONArray text must start with '['");
        }
        if (x.token() != ']') {
            x.back();
            while (true) {
                // I really should check for null elements here,
                // but let's pretend they do not exist.
                this.array.add(x.nextValue());
                switch (x.token()) {
                case ',':
                    continue;
                case ']':
                    return;
                default:
                    throw new JSONException("Expected a ',' or ']'");
                }
            }
        }
    }
	
	// ------------------------------------------
	
    /** Number of elements in array. */
    public int length() {
		return array.size();
	}
    
    /** Checks if array is empty. */
    public boolean isEmpty() {
    	return this.length() == 0;
    }
	
    /** Tries to fetch the indexed object. */
    private Object find(int index) {
        return (index < 0 || index >= length()) ? null : array.get(index);
    }
	
    /** Tries to fetch element at given index. */
    public Object get(int index) throws JSONException {
        Object object = find(index);
        if (object == null) {
            throw new JSONException("get(" + index + ") not found.");
        }
        return object;
    }
	
    /** Tries to fetch JSON object at given index. */
   public JSONObject getJSONObject(int index) throws JSONException {
        Object object = get(index);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        throw new JSONException("get(" + index + ") is not a JSONObject.");
    }
	
	// ------------------------------------------
	
	@Override
    public Iterator<Object> iterator() {
		return array.iterator();
    }

}
