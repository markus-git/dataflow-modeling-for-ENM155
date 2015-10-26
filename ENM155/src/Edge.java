
public class Edge {

	/**
	 * How much of the produced units are lost in transmission.
	 */
	private double loss;
	
	/** 
	 * How many of the demanded units should we supply. 
	 * 
	 * For example, given three nodes
	 *   - A, B and C
	 * if A gets 75% of its demand from B and 25% from C we should
	 * give these two edges a distribution value of 0.75 and 0.25,
	 * respectively.
	 */
	private double distribution;
	
	/** 
	 * For every demanded unit, how many units do we need to produce. 
	 * 
	 * For example, given two nodes
	 *   - A and B
	 * where A gets all of its demand from B. Lets say A requires heating
	 * and B provides that but at 75% efficiency. Then, for some demand 'x',
	 * B needs to produce 'x / 0.75'. Where '0.75' is the 'efficiency'.
	 */
	private double efficiency;
	private Node   node;
	
	/** Construct an edge from the given parameters. */
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
	
	/** Input node demands 'x' units from its supplier.  */
	public void demand(double x) {
		double demanded = x * distribution,
			   produced = demanded / efficiency,
			   received = produced + produced * loss;
		
		node.supply(received);
	}
}
