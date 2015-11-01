package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/** Interprets a stream of characters as JSON tokens. */
public class Tokenizer {

	/** End of file indicator. */
    private boolean eof;
    
    /** Current reading index. */
    private long index;
    
    /** Previously read character (used for backtracking). */
    private char previous;
    
    /** Stream from which we read tokens. */
    private Reader reader;
    
    /** Use or discard previous character. */
    private boolean usePrevious;
    
    /** Create a new token stream from the given reader. */
    public Tokenizer(Reader reader) {
    	if (reader.markSupported()) {
    		this.reader = reader;
    	} else {
    		this.reader = new BufferedReader(reader);
    	}
        this.eof         = false;
        this.usePrevious = false;
        this.previous    = 0;
        this.index       = 0;
    }
    
    // ------------------------------------------
    
    /** Get next character in the source string. */
    private char next() throws JSONException {
        int c;
        if (this.usePrevious) {
            this.usePrevious = false;
            c = this.previous;
        } else {
            try {
                c = this.reader.read();
            } catch (IOException exception) {
                throw new JSONException(exception);
            }
            if (c <= 0) {
            	this.eof = true;
            	c        = 0;
            }
        }
        this.index   += 1;
        this.previous = (char) c;
        return this.previous;
    }
    
    /** Get next 'n' characters in the source string. */
    private String next(int n) throws JSONException {
        char[] chars = new char[n];
        int    pos   = 0;

        while (pos < n && !end()) {
            chars[pos++] = next();
        }
        
        return new String(chars);
    }
    
    /** Get next whole token, skipping whitespace. */
    public char token() throws JSONException {
    	while (true) {
    		char c = next();
    		if (c == 0 || c > ' ') {
    			return c;
    		}
    	}
    }
    
    /** At end of file? */
    public boolean end() {
        return this.eof && !this.usePrevious;
    }
    
    /** Check if there are more tokens to consume */
    public boolean more() throws JSONException {
        next();
        if (end()) {
            return false;
        } else {
        	back();
            return true;
        }
    }
    
    /** Back up one token */
    public void back() throws JSONException {
        if (this.usePrevious || this.index <= 0) {
            throw new JSONException("Can only back up one step at a time");
        }
        this.index      -= 1;
        this.usePrevious = true;
        this.eof         = false;
    }
    
    // ------------------------------------------
    
    /** Helper for parsing entire strings. */
    public String nextString(char quote) throws JSONException {
        char c;
        StringBuilder sb = new StringBuilder();
        
        while (true) {
            c = next();
            switch (c) {
            case 0:
            case '\n':
            case '\r':
                throw new JSONException("Unterminated string");
            case '\\':
                c = next();
                switch (c) {
                case 'b':
                    sb.append('\b');
                    break;
                case 't':
                    sb.append('\t');
                    break;
                case 'n':
                    sb.append('\n');
                    break;
                case 'f':
                    sb.append('\f');
                    break;
                case 'r':
                    sb.append('\r');
                    break;
                case 'u':
                    sb.append((char)Integer.parseInt(this.next(4), 16));
                    break;
                case '"':
                case '\'':
                case '\\':
                case '/':
                    sb.append(c);
                    break;
                default:
                    throw new JSONException("Illegal escape.");
                }
                break;
            default:
                if (c == quote) {
                    return sb.toString();
                } else {
                    sb.append(c);
                }
            }
        }
    }
    
    /** Helper for parsing entire JSON items. */
    public Object nextValue() throws JSONException {
        char c = token();
        String string;

        // Check for strings, objects and arrays.
        switch (c) {
            case '"':
            case '\'':
                return nextString(c);
            case '{':
                back();
                return new JSONObject(this);
            case '[':
            	back();
            	return new JSONArray(this);
        }

        // Handle unquoted values, such as true and false.
        StringBuilder sb = new StringBuilder();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            c = next();
        }
        back();

        // If none of the above, ask JSON parser for help.
        string = sb.toString().trim();
        if (string.isEmpty()) {
            throw new JSONException("Missing value");
        } else {
            return JSONObject.stringToValue(string);
        }
    }
}
