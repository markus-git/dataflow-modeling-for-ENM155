import java.util.HashMap;
import java.util.Map;

import graph.Vertex;

public class MyVertex implements Vertex {

	private String name;
	
	private double demand;
	
	private Map<MyVertex, Double> efficiencies;
	
	public MyVertex(String name) {
		this(name, 0.0);
	}
	
	public MyVertex(String name, double demand) {
		this.name         = name;
		this.demand       = demand;
		this.efficiencies = new HashMap<>();
	}
	
	// ------------------------------------------------------------------------
	// ...
	
	public String getName() {
		return this.name;
	}
	
	public void addRate(MyVertex key, double value) {
		this.efficiencies.put(key, value);
	}
	
	// ------------------------------------------------------------------------
	// Inherited methods.
	
	@Override
	public double getDemand() {
		return this.demand;
	}

	@Override
	public void addDemand(double x) {
		this.demand += x;
	}

	@Override
	public double getEfficiency(Vertex v) {
		return this.efficiencies.get(v);
	}

}
