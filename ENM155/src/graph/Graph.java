package graph;

import java.util.Collection;

public interface Graph<V, E> {

	Collection<E> getEdges();
	
	Collection<V> getVertices();
	
	Collection<E> getIncomingEdges(V vertex);
	
	Collection<E> getOutgoingEdges(V vertex);
	
	Collection<V> getPredecessors(V vertex);
	
	Collection<V> getSuccessors(V vertex);
	
	V getSource(E edge);
	
	V getDestination(E edge);

	boolean containsEdge(E edge);
	
	boolean containsVertex(V vertex);
	
	boolean addEdge(E edge, V from, V to);
	
	boolean addVertex(V vertex);
}
