import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graph.Graph;
import graph.Pair;
import graph.directed.DirectedGraph;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.Tokenizer;

public class Main {

	public Main() {
		System.out.print("Loading example...");
		JSONObject example = readExample();
		System.out.println("\tDone!");
		
		System.out.print("Parsing example...");
		DirectedGraph<MyVertex, MyEdge> graph   = parseExample(example);
		System.out.println("\tDone!");
		
		System.out.print("Calculating demands...");
		graph.calculateDemands();
		System.out.println("\tDone!");
		
		for (MyVertex vertex : graph.getVertices()) {
			System.out.println("- Node " + vertex.getName() + " : " + vertex.getDemand());
		}
		
		System.out.print("Generating dot file...");
		String dot = graph.generateDot();
		System.out.println("\tDone!");
		
		System.out.println(graph.generateDot());
	}
	
	// ------------------------------------------------------------------------
	// Read and translate JSON example file.
	
    /** Read example file and load main JSON object. */
	private JSONObject readExample() {
    	try {
    		return new JSONObject(new Tokenizer(new BufferedReader(new FileReader("example/simple.json"))));
    	} catch (FileNotFoundException e) {
    		throw new JSONException("Could not find example file.");
    	}
	}
    
	/** Parse JSON object as a directed graph. */
	private DirectedGraph<MyVertex, MyEdge> parseExample(JSONObject example) {
		JSONObject header = example.getJSONObject("graph");
    	JSONArray  nodes  = header.getJSONArray("nodes");
    	JSONArray  edges  = header.getJSONArray("edges");
    	
    	// Fetch all the nodes, rates will be added later to not cause loops.
    	Map<String, MyVertex> ns = new HashMap<>();
    	Set<Pair<String, Pair<String, Double>>> rs = new HashSet<>();
    	for (Object o : nodes) {
    		if (o instanceof JSONObject) {
    			JSONObject node = (JSONObject) o;
    			
    			// Read fields.
    			String name     = node.getString("node-name");
    			double demand   = readDouble("node-demand", node, 0.0);
    			JSONArray rates = readArray("node-rates",   node);
    			
    			// Store rates
    			for (Object j : rates) {
    				if (j instanceof JSONObject) {
    					JSONObject rate = (JSONObject) j;
    					
    					String from = rate.getString("rate-from");
    					double eff  = readDouble("rate-efficiency", rate, 1.0);
    					
    					rs.add(new Pair<>(name, new Pair<>(from, eff)));
    				}
    			}
    			
    			// Store results.
    			ns.put(name, new MyVertex(name, demand));
    		}
    	}
    	
    	// Now that all nodes have been added we can add their rates.
    	for (Pair<String, Pair<String, Double>> pair : rs) {
    		MyVertex vertex = ns.get(pair.getKey());
    		vertex.addRate(
    				ns.get(pair.getValue().getKey()), 
    				pair.getValue().getValue());
    	}
    	
    	// Lastly we parse all edges.
    	Set<Pair<MyEdge, Pair<MyVertex, MyVertex>>> es = new HashSet<>(); 
    	for (Object o : edges) {
    		if (o instanceof JSONObject) {
    			JSONObject edge = (JSONObject) o;
    			
    			// Read fields.
    			String from  = edge.getString("edge-from");
    			String to    = edge.getString("edge-to");
    			double loss  = readDouble("edge-loss",  edge, 0.0);
    			double share = readDouble("edge-share", edge, 1.0);
    			
    			MyVertex f = ns.get(from);
    			MyVertex t = ns.get(to);
    			
    			es.add(new Pair<>(new MyEdge(loss, share), new Pair<>(f, t)));
    		}
    	}
    	
    	// We can then create the graph.
    	DirectedGraph<MyVertex, MyEdge> graph = new DirectedGraph<>();
    	for (Pair<MyEdge, Pair<MyVertex, MyVertex>> pair : es) {
    		graph.addEdge(
    				pair.getKey(), 
    				pair.getValue().getKey(), 
    				pair.getValue().getValue());
    	}
    	
		return graph;
	}
	
	/** Tries to lookup value of given field, returns 'def' if not found. */
	private double readDouble(String key, JSONObject o, double def) {
		return o.elem(key) ? o.getDouble(key) : def;
	}
	
	/** Tries to lookup value of given field */
	private JSONArray readArray(String key, JSONObject o) {
		return o.elem(key) ? o.getJSONArray(key) : new JSONArray();
	}
	
	// ------------------------------------------------------------------------
	// Main stub.
	
	public static void main(String[] args) {
		new Main();
	}

}
