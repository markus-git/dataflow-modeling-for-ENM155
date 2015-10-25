import java.util.ArrayList;
import java.util.List;

public class Node {

	private double     need;
	private List<Edge> inputs;
	
	public Node() {
		this.need   = 0;
		this.inputs = new ArrayList<>();
	}
	
	// ------------------------------------------
	
	public void init(double x) {
		need = x;
	}
	
	public double read() {
		return need;
	}
	
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
}
