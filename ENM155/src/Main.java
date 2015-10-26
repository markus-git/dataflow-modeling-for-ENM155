import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import json.JSONArray;
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
		
		buildExample(simple);
	}
	
    /** Read example file and create main JSONObject */
    private JSONObject readExample() throws FileNotFoundException {
    	return new JSONObject(
        		 new Tokenizer(
                 new BufferedReader(
                 new FileReader(
        		    "example/simple.json"))));   
	}
    
    /** Parse JSON to produce node graph */
    private List<Node> buildExample(JSONObject simple) {
    	JSONObject header = simple.getJSONObject("simple");
    	JSONArray  nodes  = header.getJSONArray("nodes");
    	JSONArray  edges  = header.getJSONArray("edges");
    	
    	List<Node>       ns  = new ArrayList<>(nodes.length());
    	Iterator<Object> nit = nodes.iterator();
    	while (nit.hasNext()) {
    		Object node = nit.next();
    		if (node instanceof JSONObject) {
    			String name = ((JSONObject) node).getString("node-name");
    			// ToDo: Read demand and mark root nodes
    			
    			ns.add(new Node(name, 0));
    			
    			System.out.println("New Node: " + name);
    		} else {
    			throw new JSONException("Node elements should be JSONObjects");
    		}
    	}
    	
    	Iterator<Object> eit = edges.iterator();
    	while (eit.hasNext()) {
    		Object edge = eit.next();
    		if (edge instanceof JSONObject) {
    			String from = ((JSONObject) edge).getString("edge-from");
    			String to   = ((JSONObject) edge).getString("edge-to");
    			// ToDo: Same as node, i.e. read rest of data fields..
    			
    			Node f = findNode(from, ns);
    			Node t = findNode(to,   ns);
    			t.connect(new Edge(
    					0.01, // loss, 
    					1.00, // distribution, 
    					0.75, // efficiency, 
    					f));
    			
    			System.out.println("New Edge from " + from + " to " + to);
    		} else {
    			throw new JSONException("Edge elements should be JSONObjects");
    		}
    	}
    	
    	return ns;
    }
    
    // This should probably be improved...
    private Node findNode(String name, List<Node> nodes) {
    	for (Node n : nodes) {
    		if (n.getName().equals(name)) {
    			return n;
    		}
    	}
    	throw new JSONException("Could not find node '" + name + "'");
    }
	
	// ------------------------------------------
	
	public static void main(String[] args) {
		new Main();
	}

}
