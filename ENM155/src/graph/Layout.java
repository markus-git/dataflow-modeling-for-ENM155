package graph;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Layout<V, E> {

	private Set<V>          staticVertices;
	private Map<V, Point2D> vertices;
	
	private Dimension   size;
	private Graph<V, E> graph;
	
	public Layout(Graph<V, E> graph, Dimension size) {
		this.staticVertices = new HashSet<>();
		this.vertices       = new HashMap<>();
		this.size           = size;
		this.graph          = graph;
	}
	
	public abstract void initialize();
	
	public void updateDimension(Dimension size) {
		if (size == null) {
			throw new IllegalArgumentException("Cannot set size to null");
		}
		
		adjustVertices(size, this.size);
		this.size = size;
	}
	
	private void adjustVertices(Dimension n, Dimension o) {
		int xo = (n.width  - o.width)  / 2;
		int yo = (n.height - o.height) / 2;
		
		for (V vertex : graph.getVertices()) {
			adjustVertex(vertex, xo, yo);
		}
	}
	
	private void adjustVertex(V vertex, int xo, int yo) {
		Point2D coords = getCoordinates(vertex);
		coords.setLocation(coords.getX() + xo, coords.getY() + yo);
		setCoordniates(vertex, coords);
	}
	
	public Point2D getCoordinates(V vertex) {
		return vertices.get(vertex);
	}
	
	public void setCoordniates(V vertex, Point2D coords) {
		vertices.get(vertex).setLocation(coords);
	}
	
	public void freeze(V vertex) {
		staticVertices.add(vertex);
	}
	
	public void thaw(V vertex) {
		staticVertices.remove(vertex);
	}
}
