import java.util.HashMap;
import java.util.Map;

import graph.Vertex;

/** An implementation of the Vertex interface. */
public class MyVertex implements Vertex {

	/** Name given to this node. */
	private String name;
	
	/** Number of units consumed by this vertex. */
	private double demand;
	
	/** Mapping over transfer efficiencies between this node and others. */
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
	
	/** Introduce a new transfer mapping. */
	public void addRate(MyVertex key, double value) {
		this.efficiencies.put(key, value);
	}
	
	// ------------------------------------------------------------------------
	// Inherited methods.
	
	@Override
	public String getLabel() {
		return this.name;
	}
	
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
