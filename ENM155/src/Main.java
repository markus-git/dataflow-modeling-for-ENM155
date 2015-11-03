import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import graph.directed.MyEdge;
import graph.directed.MyGraph;
import graph.directed.MyVertex;
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
		MyGraph<MyVertex, MyEdge> graph   = parseExample(example);
		System.out.println("\tDone!");
		
		System.out.print("Calculating demands...");
		graph.calculateDemands();
		System.out.println("\tDone!");
		
		System.out.print("Generating dot file...");
		String dot = graph.generateDot();
		System.out.println("\tDone!");
		
		// The output of graph.generateDot() can be used to visualize your graph.
		System.out.println('\n' + dot);
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
	private MyGraph<MyVertex, MyEdge> parseExample(JSONObject example) {
		JSONArray vertices = example.getJSONArray("vertices");
		
		// Read vertices, 'vs', and their inputs, 'is.
		Map<String, JSONArray> is = new HashMap<>();
		Map<String, MyVertex>  vs = new HashMap<>();
		for (Object o : vertices) {
			if (o instanceof JSONObject) {
				JSONObject vertex = (JSONObject) o;
				
				// Read and store vertex fields.
				String    name   = vertex.getString("name");
				double    demand = readDouble("demand", vertex, 0);
				vs.put(name, new MyVertex(name, demand));

				// Read and store inputs, we'll add these once all vertices are read.
				if (vertex.elem("inputs")) {
					is.put(name, vertex.getJSONArray("inputs"));
				}
			}
		}
		
		// Create graph from the parsed vertices and edges.
		MyGraph<MyVertex, MyEdge> graph = new MyGraph<>();
		for (Entry<String, JSONArray> e : is.entrySet()) {
			MyVertex destination = vs.get(e.getKey());
			for (Object o : e.getValue()) {
				if (o instanceof JSONObject) {
					JSONObject edge = (JSONObject) o;
					
					// Read fields.
					String name       = edge.getString("name");
					double share      = readDouble("share",      edge, 1);
					double efficiency = readDouble("efficiency", edge, 1);
					MyVertex source   = vs.get(name);
					
					// Update source vertex.
					source.addRate(destination, efficiency);
					
					// Store new edge.
					graph.addEdge(new MyEdge(share), source, destination);
				}
			}
		}
    	
		return graph;
	}
	
	/** Tries to lookup value of given field, returns 'def' if not found. */
	private double readDouble(String key, JSONObject o, double def) {
		return o.elem(key) ? o.getDouble(key) : def;
	}
	
	// ------------------------------------------------------------------------
	// Main stub.
	
	public static void main(String[] args) {
		new Main();
	}

}
