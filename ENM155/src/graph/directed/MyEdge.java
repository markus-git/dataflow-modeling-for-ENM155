package graph.directed;
import graph.Edge;

/** An implementation of the Edge interface. */
public class MyEdge implements Edge {

	/** Percentage of total demand drawn from this edge. */
	private double share;
	
	/** Instantiate an edge with the given loss and share percentages. */
	public MyEdge(double share) {
		this.share = share;
	}
	
	// ------------------------------------------------------------------------
	// Inherited methods.

	@Override
	public double getShare() {
		return this.share;
	}

}
