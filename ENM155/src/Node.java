import java.util.ArrayList;
import java.util.List;

public class Node {

	/** Name given to the node. */
	private String     name;
	
	/** 
	 * Number of units needed to meet demand of node.
	 * 
	 * For most nodes, this would initially be zero. Nodes
	 * with a non-zero (root nodes) need must require their 
	 * inputs to supply the sought amount 
	 * (using the 'demand()' function).
	 */
	private double     need;
	
	/** Nodes connected as suppliers to this node, over some edge. */
	private List<Edge> inputs;
	
	/** Construct a node with the given name and initial demand. */
	public Node(String name, double need) {
		this.name   = name;
		this.need   = need;
		this.inputs = new ArrayList<>();
	}
	
	// ------------------------------------------
	
	/** 
	 * Supply the demanded amount and, in turn, require the
	 * necessary supply from our inputs.
	 */
	public void supply(double x) {
		need += x;
		demand();
	}
	
	/** Demand the needed amount from out input nodes. */
	public void demand() {
		for (Edge e : inputs)
			e.demand(need);
	}
	
	/** Connect an input to this node. */
	public void connect(Edge e) {
		inputs.add(e);
	}
	
	// ------------------------------------------
	
	/** Gets the node's name. */
	public String getName() {
		return name;
	}
	
	/** Gets the node's current demand. */
	public double getDemand() {
		return need;
	}
}
