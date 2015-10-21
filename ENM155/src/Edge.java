
public class Edge {

	private double loss;
	private double distribution;
	private double efficiency;
	private Node   node;
	
	public Edge( double loss
			   , double distribution
			   , double efficiency
			   , Node node) 
	{
		this.loss         = loss;
		this.distribution = distribution;
		this.efficiency   = efficiency;
		this.node         = node;
	}
	
	// ------------------------------------------
	
	public void demand(double x) {
		double demanded = x * distribution,
			   produced = demanded / efficiency,
			   received = produced + produced * loss;
		
		node.supply(received);
	}
}
