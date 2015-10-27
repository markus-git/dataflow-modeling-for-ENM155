import graph.Edge;

public class MyEdge implements Edge {

	private double loss;
	
	private double share;
	
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
