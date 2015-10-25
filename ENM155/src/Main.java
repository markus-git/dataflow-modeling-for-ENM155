import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import json.JSONException;
import json.JSONObject;
import json.Tokenizer;

public class Main {

    private Main() {
		JSONObject simple;
		
		try {
			simple = readExample();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("could not load example file");
		}
		
		// Do stuff...
	}
	
    private JSONObject readExample() throws FileNotFoundException, JSONException {
    	return new JSONObject(
        		 new Tokenizer(
                 new BufferedReader(
                 new FileReader(
        		    "example/simple.json"))));   
	}
	
	// ------------------------------------------
	
	public static void main(String[] args) {
		new Main();
	}

}
