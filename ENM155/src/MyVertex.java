import java.util.HashMap;
import java.util.Map;

import graph.Vertex;

/** An implementation of the Vertex interface. */
public class MyVertex implements Vertex {

	/** Name given to this node. */
	private String name;
	
	/** Number of units produced by this vertex. */
	private double output;
	
	/** Mapping over transfer efficiencies between this node and others. */
	private Map<MyVertex, Double> efficiencies;
	
	public MyVertex(String name) {
		this(name, 0.0);
	}
	
	public MyVertex(String name, double output) {
		this.name         = name;
		this.output       = output;
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
	public double getOutput() {
		return this.output;
	}

	@Override
	public void demandInput(double x) {
		this.output += x;
	}

	@Override
	public double getEfficiency(Vertex v) {
		return this.efficiencies.get(v);
	}

}
