import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.RuntimeErrorException;

import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.Tokenizer;

public class Main {

    private Main() {
    	
    	// Build example.
    	System.out.println("-- Construction");
		JSONObject simple = readExample();
		List<Node> nodes  = buildExample(simple);
	
		// Demand supply for each node that needs it.
		for (Node n : nodes) {
			n.demand();
		}
		
		// Display node values.
		System.out.println("-- Values");
		for (Node n : nodes) {
			System.out.println("Node " + n.getName() + " needs " + n.getDemand());
		}
		
		// Display graph as .dot file.
		showExample(nodes);
	}
	
    /** Read example file and create main JSONObject */
    private JSONObject readExample() {
    	try {
    	    return new JSONObject(
        	         new Tokenizer(
                       new BufferedReader(
                         new FileReader(
        		           "example/simple.json"))));
    	} catch (FileNotFoundException e) {
    		throw new JSONException("Could not find example file.");
    	}
	}
    
    /** Parse JSON to produce node graph */
    private List<Node> buildExample(JSONObject simple) {
    	JSONObject header = simple.getJSONObject("simple");
    	JSONArray  nodes  = header.getJSONArray("nodes");
    	JSONArray  edges  = header.getJSONArray("edges");
    	
    	// Read nodes.
    	List<Node>       ns  = new ArrayList<>(nodes.length());
    	Iterator<Object> nit = nodes.iterator();
    	while (nit.hasNext()) {
    		Object node = nit.next();
    		if (node instanceof JSONObject) {
    			JSONObject o = (JSONObject) node;
    			
    			// Read fields.
    			String name = o.getString("node-name");
    			double need = readDefault("node-demand", o, 0.0);
    		    
    			// Add new node.
    			ns.add(new Node(name, need));
    			
    			// Print name if successfully added.
    			System.out.println("New Node: " + name);
    		} else {
    			throw new JSONException("Node elements should be JSONObjects");
    		}
    	}
    	
    	// Read edges.
    	Iterator<Object> eit = edges.iterator();
    	while (eit.hasNext()) {
    		Object edge = eit.next();
    		if (edge instanceof JSONObject) {
    			JSONObject o = (JSONObject) edge;
    			
    			// Read fields.
    			String from = o.getString("edge-from");
    			String to   = o.getString("edge-to");
    			double loss = readDefault("edge-loss", o, 0.0);
    			double dist = readDefault("edge-distribution", o, 1.0);
    			double eff  = readDefault("edge-efficiency", o, 1.0);
    			
    			// Lookup nodes referenced and add new edge.
    			Node f = findNode(from, ns);
    			Node t = findNode(to,   ns);
    			t.connect(new Edge(loss, dist, eff, f));
    			
    			// Print connection if successfully added.
    			System.out.println("New Edge from " + from + " to " + to);
    		} else {
    			throw new JSONException("Edge elements should be JSONObjects");
    		}
    	}
    	
    	return ns;
    }
    
    /** Display example file as a pdf using dot. (Or using http://webgraphviz.com with the generated dot file) */
    private void showExample(List<Node> nodes) {
		try {
			Writer writer = new BufferedWriter(
					          new OutputStreamWriter(
	                            new FileOutputStream("example/simple.dot"), "utf-8"));
			writer.write("digraph simple {\n");
			for (Node n : nodes) {
				for (Edge e : n.getInputs()) {
					writer.write('\t' + e.getOrigin().getName() + " -> " + n.getName() + '\n');
				}
			}
			writer.write('}');
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not write 'dot' file.", e);
		}
    }
    
    /** Tries to lookup value of given field, returns 'def' if not found. */
    private double readDefault (String key, JSONObject o, double def) {
    	return o.elem(key) ? o.getDouble(key) : def;
    }
    
    /** Tries to find the referenced Node in the given list. */
    private Node findNode(String name, List<Node> nodes) {
    	// This should probably be improved...
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
