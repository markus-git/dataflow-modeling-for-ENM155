package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DAG<V, E> implements Graph<V, E> {

	private Map<V, Pair<Set<E>, Set<E>>> vertices;
	private Map<E, Pair<V, V>>           edges;
	
	public DAG() {
		this.vertices = new HashMap<>();
		this.edges    = new HashMap<>();
	}
	
	// ------------------------------------------
	
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
			throw new GraphException("Could not find vertex");
		} else {
			return vertices.get(vertex).fst();
		}
	}

	@Override
	public Collection<E> getOutgoingEdges(V vertex) {
		if (!containsVertex(vertex)) {
			throw new GraphException("Could not find vertex");
		} else {
			return vertices.get(vertex).snd();
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
			return edges.get(edge).fst();
		}
	}

	@Override
	public V getDestination(E edge) {
		if (!containsEdge(edge)) {
			throw new GraphException("Could not find edge.");
		} else {
			return edges.get(edge).snd();
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
			throw new GraphException("Cannot add edges where either endpoint is null.");
		}
			
		addVertex(from);
		addVertex(to);
		edges.put(edge, new Pair<>(from, to));
		
		return true;
	}

	@Override
	public boolean addVertex(V vertex) {
		if (vertex == null) {
			throw new GraphException("Cannot add null vertices.");
		}
		if (!containsVertex(vertex)) {
			vertices.put(vertex, new Pair<>(new HashSet<>(), new HashSet<>()));
			return true;
		} else {
		    return false;	
		}
	}
}
