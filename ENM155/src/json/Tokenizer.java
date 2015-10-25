package json;

import java.io.BufferedReader;
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
        this.reader = reader.markSupported()
            ? reader
            : new BufferedReader(reader);
        this.eof = false;
        this.usePrevious = false;
        this.previous = 0;
        this.index = 0;
        this.character = 1;
        this.line = 1;
    }

    public Tokenizer(InputStream inputStream) throws JSONException {
        this(new InputStreamReader(inputStream));
    }
    
    // ------------------------------------------
    
    
}
