package graph;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DAGLayout<V, E> extends Layout<V, E> {

	private Map<V, Integer> levels;
	private int             height;
	
	private int epoch;
	private int epochMax;
	private int interval;
	
	private int radius;
	private int radiusMin;
	
	private double adaptation;
	private double adaptationMax;
	private double adaptationMin;
	private double coolingfactor;
	
	private final double PADDING = 1.2;
	
	public DAGLayout(Graph<V, E> graph, Dimension size) {
		super(graph, size);
		
		this.levels = new HashMap<>();
		this.height = 0;
	}

	public void setRoots(Collection<V> vertices) {
		for (V vertex : vertices) {
			levels.put(vertex, 0);
			propagateLevels(vertex);
		}
		
		this.height = Collections.max(levels.values());
	}
	
	private void propagateLevels(V v) {
		int level = levels.get(v);
		for (V child : getGraph().getSuccessors(v)) {
			if (!levels.containsKey(child) || levels.get(child) < level + 1) {
				levels.put(child, level + 1);
			}
			propagateLevels(child);
		}
	}
	
	// ------------------------------------------
	
	private void initializeLocations(Dimension size) {
		for (V vertex : getGraph().getVertices()) {
			int level = this.levels.get(vertex);
			int minY  = (int) (size.height * level / (this.height * PADDING));
			
			double x  = Math.random() * size.width;
			double y  = Math.random() * (size.height - minY) + minY;
			
			getCoordinates(vertex).setLocation(x, y);
		}
	}
	
	@Override
	public void updateDimension(Dimension size) {
		super.updateDimension(size);
		
		initializeLocations(size);
	}
	
	@Override
	public void initialize() {
		// Based on http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.37.6856&rep=rep1&type=pdf,
		// with semi-random starting points (based on levels).
		initializeLocations(getSize());
		
		this.epoch    = 1;
		this.epochMax = 1000;
		this.interval = 100;
		
		this.radius    = 5;
		this.radiusMin = 1;
		
		this.adaptation    = 90.0 / 100.0;
		this.adaptationMax = adaptation;
		this.adaptationMin = 0;
		this.coolingfactor = 2;
	}
	
	// ------------------------------------------
	
	private void step() {
		if (epoch < epochMax) {
			adjust();
			update();
		}
	}
	
	private void adjust() {
		// Creates a new random point location.
		Point2D rnd = new Point2D.Double();
	    rnd.setLocation(
				10 + Math.random() * getSize().width, 
				10 + Math.random() * getSize().height);

	    // Select point closest to rnd
	    int distance = Integer.MAX_VALUE;
		V   winner   = null;
		for (V vertex : getGraph().getVertices()) {
			if (getCoordinates(vertex).distance(rnd) < distance) {
				winner = vertex;
			}
		}

		adjustVertex(winner, rnd);
	}
	
	private void adjustVertex(V vertex, Point2D p) {
		if (vertex == null) {
			throw new GraphException("Cannot adjust position of null vertex (no vertices found?).");
		}
		
		// ToDo: ...
	}
	
	private void update() {
		double e = Math.exp(-coolingfactor * (epoch / epochMax));
		
		this.adaptation = Math.max(adaptationMin, e * adaptationMax);
		this.epoch     += 1;
		if (radius > radiusMin && epoch % interval == 0) {
			this.radius -= 1;
		}
	}
	
	// ------------------------------------------
	
	public Graph<V, E> getLayout() {
		return super.getGraph();
	}
}
