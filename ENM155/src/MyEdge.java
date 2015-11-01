import graph.Edge;

/** An implementation of the Edge interface. */
public class MyEdge implements Edge {

	/** Percentage of lost units during transmission. */
	private double loss;
	
	/** Percentage of total demand drawn from this edge. */
	private double share;
	
	/** Instantiate an edge with the given loss and share percentages. */
	public MyEdge(double loss, double share) {
		this.loss  = loss;
		this.share = share;
	}
	
	// ------------------------------------------------------------------------
	// Inherited methods.
	
	@Override
	public double getLoss() {
		return this.loss;
	}

	@Override
	public double getShare() {
		return this.share;
	}

}
