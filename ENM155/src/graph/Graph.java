package graph;

import java.util.Collection;

/**
 * Abstract representation of directed graphs: A collection of
 * vertices and edges. Support for generation of .dot files is
 * also included, in order to visualize graphs easily.
 *
 * @param <V> - Kind used for vertices.
 * @param <E> - Kind used for edges.
 */
public interface Graph<V, E> {

	/** Edges used in graph. */
	Collection<E> getEdges();
	
	/** Vertices used in graph. */
	Collection<V> getVertices();
	
	/** Fetches all incoming edges associated with a vertex. */
	Collection<E> getIncomingEdges(V vertex);
	
	/** Fetches all outgoing edges associated with a vertex. */
	Collection<E> getOutgoingEdges(V vertex);
	
	/** Fetches all vertices connected to an edge ending the given vertex. */
	Collection<V> getPredecessors(V vertex);
	
	/** Fetches all vertices connected to an edge starting in the given vertex. */
	Collection<V> getSuccessors(V vertex);
	
	/** Fetches the source vertex of the given edge. */
	V getSource(E edge);
	
	/** Fetches the destination vertex of the given edge. */
	V getDestination(E edge);

	/** Checks if the graph contains the given edge. */
	boolean containsEdge(E edge);
	
	/** Checks if the graph contains the given vertex. */
	boolean containsVertex(V vertex);
	
	/** Inserts an edge between the two vertices, adding the vertices if necessary. */
	boolean addEdge(E edge, V from, V to);
	
	/** Inserts a vertex into the graph. */
	boolean addVertex(V vertex);
	
	/** Generates a .dot file representation of the graph. */
	String generateDot();
}
