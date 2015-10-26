import java.util.ArrayList;
import java.util.List;

public class Node {

	private String     name;
	private double     need;
	private List<Edge> inputs;
	
	public Node(String name, double need) {
		this.name   = name;
		this.need   = need;
		this.inputs = new ArrayList<>();
	}
	
	// ------------------------------------------
	
	public void supply(double x) {
		need += x;
		demand();
	}
	
	public void demand() {
		for (Edge e : inputs)
			e.demand(need);
	}
	
	public void connect(Edge e) {
		inputs.add(e);
	}
	
	// ------------------------------------------
	
	public String getName() {
		return name;
	}
	
	public double getDemand() {
		return need;
	}
}
