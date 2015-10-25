package json;

import java.util.ArrayList;
import java.util.Iterator;

public class JSONArray implements Iterable<Object> {

	private final ArrayList<Object> array;
	
	public JSONArray() {
        this.array = new ArrayList<Object>();
    }
	
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
	
	public int length() {
		return array.size();
	}
	
	public Object find(int index) {
        return (index < 0 || index >= length()) ? null : array.get(index);
    }
	
	public Object get(int index) throws JSONException {
        Object object = this.find(index);
        if (object == null) {
            throw new JSONException("JSONArray[" + index + "] not found.");
        }
        return object;
    }
	
	public JSONObject getJSONObject(int index) throws JSONException {
        Object object = this.get(index);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
    }
	
	// ------------------------------------------
	
	@Override
	public Iterator<Object> iterator() {
		return array.iterator();
	}

}
