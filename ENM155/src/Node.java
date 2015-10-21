import java.util.ArrayList;
import java.util.List;

public class Node {

	private double     demand;
	private List<Edge> inputs;
	
	public Node() {
		this.demand = 0;
		this.inputs = new ArrayList<>();
	}
	
	// ------------------------------------------
	// Input/Output
	
	public void init(double x) {
		demand = x;
	}
	
	public double read() {
		return demand;
	}
	
	// ------------------------------------------
	// Calculations
	
	public void supply(double x) {
		demand += x;
		demand();
	}
	
	public void demand() {
		for (Edge e : inputs)
			e.demand(demand);
	}
	
	// ------------------------------------------
	// Connections
	
	public void connect(Edge e) {
		inputs.add(e);
	}
}
