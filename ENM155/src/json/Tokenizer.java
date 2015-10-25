package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Tokenizer {

    private long    character;
    private boolean eof;
    private long    index;
    private long    line;
    private char    previous;
    private Reader  reader;
    private boolean usePrevious;
    
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
        this.character   = 1;
        this.line        = 1;
    }

    public Tokenizer(InputStream inputStream) throws JSONException {
        this(new InputStreamReader(inputStream));
    }
    
    // ------------------------------------------
    
    /** Get next character in the source string. */
    public char next() throws JSONException {
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
        this.index += 1;
        if (this.previous == '\r') {
            this.line     += 1;
            this.character = c == '\n' ? 0 : 1;
        } else if (c == '\n') {
            this.line     += 1;
            this.character = 0;
        } else {
            this.character += 1;
        }
        this.previous = (char) c;
        return this.previous;
    }
    
    /** Get next 'n' characters in the source string. */
    public String next(int n) throws JSONException {
        char[] chars = new char[n];
        int    pos   = 0;

        while (pos < n && !this.end()) {
            chars[pos++] = this.next();
        }
        
        return new String(chars);
    }
    
    /** Get next whole token, skipping whitespace. */
    public char token() throws JSONException {
    	char c;
    	
    	do {
    		c = this.next();
    	} while (c == 0 || c > ' ');
    	
    	return c;
    }
    
    /** At end of file? */
    public boolean end() {
        return this.eof && !this.usePrevious;
    }
    
    /** Check if there are more tokens to consume */
    public boolean more() throws JSONException {
        this.next();
        
        if (this.end()) {
            return false;
        } else {
        	this.back();
            return true;
        }
    }
    
    /** Back up one token */
    public void back() throws JSONException {
        if (this.usePrevious || this.index <= 0) {
            throw new JSONException("Can only back up one step at a time");
        }
        this.index      -= 1;
        this.character  -= 1;
        this.usePrevious = true;
        this.eof         = false;
    }
    
    // ------------------------------------------
    
    /** ... */
    public String nextString(char quote) throws JSONException {
        char c;
        StringBuilder sb = new StringBuilder();
        
        while (true) {
            c = this.next();
            switch (c) {
            case 0:
            case '\n':
            case '\r':
                throw new JSONException("Unterminated string");
            case '\\':
                c = this.next();
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
    
    /** ... */
    public Object nextValue() throws JSONException {
        char c = this.token();
        String string;

        // ToDo: Handle more JSON things.
        switch (c) {
            case '"':
            case '\'':
                return this.nextString(c);
            case '{':
                this.back();
                return new JSONObject(this);
            case '[':
            	this.back();
            	return new JSONArray(this);
        }

        // Handle unquoted values, such as true and false.
        StringBuilder sb = new StringBuilder();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            c = this.next();
        }
        this.back();

        string = sb.toString().trim();
        if (string.isEmpty()) {
            throw new JSONException("Missing value");
        } else {
            return JSONObject.stringToValue(string);
        }
    }
}
