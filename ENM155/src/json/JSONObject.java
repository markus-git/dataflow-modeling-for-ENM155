package json;

import java.util.HashMap;
import java.util.Map;

public class JSONObject {
	
	/** Mapping over fields in JSON object. */
    private final Map<String, Object> objects;
	
    private JSONObject() {
        this.objects = new HashMap<String, Object>();
    }
	
    /** Construct a JSONObject from the given token stream. */
    public JSONObject(Tokenizer x) throws JSONException {
        this();
        String key;

        if (x.token() != '{') {
            throw new JSONException("A JSONObject text must begin with '{'");
        }
        
        while (true) {
            switch (x.token()) {
            case 0:
                throw new JSONException("A JSONObject text must end with '}'");
            case '}':
                return;
            default:
                x.back();
                key = x.nextValue().toString();
            }

            // The key is followed by ':'.
            if (x.token() != ':') {
                throw new JSONException("Expected a ':' after a key");
            }
            putObject(key, x.nextValue());

            // Pairs are separated by ','.
            switch (x.token()) {
            case ';':
            case ',':
                continue;
            case '}':
                return;
            default:
                throw new JSONException("Expected a ',' or '}'");
            }
        }
    }

	// ------------------------------------------
    
    /** Tries to find indexed object */
    public boolean elem(String key) {
    	return find(key) == null ? false : true;
    }
    
    private Object find(String key) {
        return key == null ? null : this.objects.get(key);
    }
	
	/** Deletes a key/value pair from store. */
    public Object remove(String key) {
        return this.objects.remove(key);
    }
	
    private JSONObject put(String key, Object value) throws JSONException {
        if (key == null) {
            throw new NullPointerException("Null key.");
        }
        if (value != null) {
            this.objects.put(key, value);
        } else {
            remove(key);
        }
        return this;
    }
	
	/** Insert object into store iff key is unique. */
    public JSONObject putObject(String key, Object value) throws JSONException {
        if (key != null && value != null) {
            if (find(key) != null) {
                throw new JSONException("Duplicate key \"" + key + "\"");
            }
            put(key, value);
        }
        return this;
    }
	
	// ------------------------------------------
	
	/** Tries to parse the string as a value. */
    public static Object stringToValue(String string) {
        if (string.isEmpty()) {
            return string;
        }
        
        // Boolean?
        if (string.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (string.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }

        // Number?
        char b = string.charAt(0);
        if (Character.isDigit(b) || b == '-') {
            try {
            	// Double or Integer?
                if (string.indexOf('.') > -1 
                		|| string.indexOf('e') > -1
                        || string.indexOf('E') > -1) 
                {
                    Double d = Double.valueOf(string);
                    if (!d.isInfinite() && !d.isNaN()) {
                        return d;
                    }
                } else {
                	Integer i = new Integer(string);
                	if (string.equals(i.toString())) {
                		return i;
                	}
                }
            } catch (Exception ignore) {
            	// Must have been something else.
            }
        }
        
        // Has to be a String then..
        return string;
    }
	
	// ------------------------------------------
	
	/** Tries to fetch the object associated with the key. */
    public Object get(String key) throws JSONException {
        if (key == null) {
            throw new NullPointerException("Null key.");
        }
        
        Object object = find(key);
        if (object == null) {
            throw new JSONException("get(" + key + ") not found (null).");
        } else {
            return object;
        }
    }
    
    /** Tries to fetch a string associated with the key. */
    public String getString(String key) throws JSONException {
        Object object = get(key);
        if (object instanceof String) {
            return (String) object;
        } else {
            throw new JSONException("get(" + key + ") is not a string.");
        }
    }
	
	/** Tries to fetch an boolean associated with the key. */
    public boolean getBoolean(String key) throws JSONException {
        Object object = this.get(key);
        if (object.equals(Boolean.FALSE)
                || (object instanceof String && ((String) object)
                        .equalsIgnoreCase("false"))) {
            return false;
        } else if (object.equals(Boolean.TRUE)
                || (object instanceof String && ((String) object)
                        .equalsIgnoreCase("true"))) {
            return true;
        } else {
            throw new JSONException("get(" + key + ") is not a Boolean.");
        }
    }
	
	/** Tries to fetch a double associated with the key. */
    public double getDouble(String key) throws JSONException {
        Object object = this.get(key);
        try {
        	if (object instanceof Number) {
        		return ((Number) object).doubleValue();
        	} else {
        		return Double.parseDouble((String) object);
        	}
        } catch (Exception e) {
            throw new JSONException("get(" + key + ") is not a number.");
        }
    }
	
	/** Tries to fetch an integer associated with the key. */
    public int getInteger(String key) throws JSONException {
        Object object = get(key);
        try {
        	if (object instanceof Number) {
        		return ((Number) object).intValue();
        	} else {
        		return Integer.parseInt((String) object);
        	}
        } catch (Exception e) {
            throw new JSONException("get(" + key + ") is not an int.");
        }
    }
    
    public JSONObject getJSONObject(String key) throws JSONException {
        Object object = this.get(key);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        } else {
            throw new JSONException("get(" + key + ") is not a JSONObject.");
        }
    }
    
    public JSONArray getJSONArray(String key) throws JSONException {
        Object object = get(key);
        if (object instanceof JSONArray) {
            return (JSONArray) object;
        } else {
            throw new JSONException("get(" + key + ") is not a JSONArray.");
        }
    }
}
