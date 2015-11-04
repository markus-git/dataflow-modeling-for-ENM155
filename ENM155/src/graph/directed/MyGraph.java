package graph.directed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graph.*;

/**
 * A implementation of the graph interface. Also provides a sample
 * algorithm for solving the demand problem.
 * 
 * @param <V> - Kind used for vertices, assumed to extend Vertex interface.
 * @param <E> - Kind used for edges, assumed to extend Edge interface.
 */
public class MyGraph<V extends Vertex, E extends Edge> implements Graph<V, E> {

	/** Vertices are combined with a set of incoming/outgoing edges for each vertex. */
	private Map<V, Pair<Set<E>, Set<E>>> vertices;
	
	/** Edges are combined with their source and destination vertices. */
	private Map<E, Pair<V, V>> edges;
	
	/** Topological ordering of vertices with similar levels grouped (currently not used). */
	private Map<V, Integer> levels;
	
	/** Maximum height of graph according to topological ordering (currently not used). */
	@SuppressWarnings("unused") private int height;
	
	/** Creates an empty directed graph. */
	public MyGraph() {
		this.vertices = new HashMap<V, Pair<Set<E>, Set<E>>>();
		this.edges    = new HashMap<E, Pair<V, V>>();
		this.levels   = new HashMap<V, Integer>();
		this.height   = 0;
	}
	
	// ------------------------------------------------------------------------
	// Implementation of a Input/Output satisfy algorithm.
	
	/** Calculate the amount of units required to meet initial demand.  */
	public void calculateDemands() {
		for (V vertex : getVertices()) {
			propagateDemand(vertex);
		}
	}
	
	/** Propagate the demands of one node through the graph. */
	private void propagateDemand(V v) {
		// Calculate demanded output of vertex.
		double output = v.getOutput() - v.getInput();
		
		// Request the necessary supply from all predecessors to satisfy output. 
		for (E edge : getIncomingEdges(v)) {
			V source = getSource(edge);
			
			// Calculate new demand.
			double consumed = output           // Consumed units, adjusted for
					* edge.getShare()          //  - our share 
					/ source.getEfficiency(v); //  - efficiency loss
			
			// Update for new demand.
			source.demandInput(consumed);
			propagateDemand(source);
		}
		
		// Once we have finished all requests, mark output as satisfied.
		v.satisfyOutput();
	}
	
	// ------------------------------------------------------------------------
	// Visualization.
	
	@Override
	public String generateDot() {
		calculateLevels();
		
		StringBuilder dot = new StringBuilder();
		dot.append("digraph G {\n");
		dot.append("\trankdir=LR\n");
		dot.append("\tnodesep=1.0\n");
		dot.append("\tnode[shape=Mrecord]\n\n");
		
		// Print vertices.
		for (V v : getVertices()) {
			dot.append("\t" +
					  v.getLabel()  + "[ label=\"<f0> " + 
					  v.getLabel()  + " |<f1> " +
					  round(v.getOutput(), 2) + " \" ];\n"
					);
		}
		
		dot.append('\n');
		
		// Print edges.
		for (E e : getEdges()) {
			dot.append("\t" +
					  getSource(e).getLabel() + " -> " +
					  getDestination(e).getLabel() + " [ label = \"" +
					  round(e.getShare() * 100, 3) + "% \" ];\n"
					);
		}
		
		dot.append('}');
		
		return dot.toString();
	}
	
	/** Round a double to limit the number of decimals used during visualization. */
	private double round(double value, int places) {
		 return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
	}
	
	/** Give a topological order to graph vertices, grouping similar levels. */
	private void calculateLevels() {
		for (V vertex : getVertices()) {
			if (getPredecessors(vertex).isEmpty()) {
				levels.put(vertex, 0);
				propagateLevels(vertex);
			}
		}
		
		this.height = Collections.max(levels.values());
	}
	
	/** Propagate the level of one vertex to its predecessors. */
	private void propagateLevels(V v) {
		int level = levels.get(v);
		for (V child : getSuccessors(v)) {
			if (!levels.containsKey(child) || levels.get(child) < level + 1) {
				levels.put(child, level + 1);
			}
			propagateLevels(child);
		}
	}
	
	// ------------------------------------------------------------------------
	// Inherited methods.
	
	@Override
	public Collection<E> getEdges() {
		return this.edges.keySet();
	}

	@Override
	public Collection<V> getVertices() {
		return this.vertices.keySet();
	}

	@Override
	public Collection<E> getIncomingEdges(V vertex) {
		if (!containsVertex(vertex)) {
			throw new GraphException("Could not find vertex.");
		} else {
			return vertices.get(vertex).getKey();
		}
	}

	@Override
	public Collection<E> getOutgoingEdges(V vertex) {
		if (!containsVertex(vertex)) {
			throw new GraphException("Could not find vertex");
		} else {
			return vertices.get(vertex).getValue();
		}
	}

	@Override
	public Collection<V> getPredecessors(V vertex) {
		if (!containsVertex(vertex)) {
			throw new GraphException("Could not find vertex");
		} 
		
		Set<V> predecessors = new HashSet<>();
		for (E edge : getIncomingEdges(vertex)) {
			predecessors.add(getSource(edge));
		}
		
		return predecessors;
	}

	@Override
	public Collection<V> getSuccessors(V vertex) {
		if (!containsVertex(vertex)) {
			throw new GraphException("Could not find vertex.");
		}
		
		Set<V> successors = new HashSet<>();
		for (E edge : getOutgoingEdges(vertex)) {
			successors.add(getDestination(edge));
		}
		
		return successors;
	}

	@Override
	public V getSource(E edge) {
		if (!containsEdge(edge)) {
			throw new GraphException("Could not find edge.");
		} else {
			return edges.get(edge).getKey();
		}
	}

	@Override
	public V getDestination(E edge) {
		if (!containsEdge(edge)) {
			throw new GraphException("Could not find edge.");
		} else {
			return edges.get(edge).getValue();
		}
	}

	@Override
	public boolean containsEdge(E edge) {
		return getEdges().contains(edge);
	}

	@Override
	public boolean containsVertex(V vertex) {
		return getVertices().contains(vertex);
	}

	@Override
	public boolean addEdge(E edge, V from, V to) {
		if (edge == null) {
			throw new GraphException("Cannot add null edges.");
		} else if (from == null || to == null) {
			throw new GraphException("Cannot add edges where any endpoint is null.");
		}
			
		addVertex(from);
		addVertex(to);
		
		getOutgoingEdges(from).add(edge);
		getIncomingEdges(to).add(edge);
		
		edges.put(edge, new Pair<V, V>(from, to));
		
		return true;
	}

	@Override
	public boolean addVertex(V vertex) {
		if (vertex == null) {
			throw new GraphException("Cannot add null vertices.");
		}
		if (!containsVertex(vertex)) {
			vertices.put(
					vertex, 
					new Pair<Set<E>, Set<E>>(
							new HashSet<E>(), 
							new HashSet<E>()));
			
			return true;
		} else {
		    return false;	
		}
	}

}
