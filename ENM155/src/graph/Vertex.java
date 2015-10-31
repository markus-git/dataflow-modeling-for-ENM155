package graph;

/** Necessary vertex attributes for solving the demand problem. */
public interface Vertex {

	/** Name assigned to vertex. */
	String getLabel();
	
	/** Current output of vertex. */
	double getOutput();
	
	/** Request additional supply from this vertex, increasing its output. */
	void demandInput(double x);
	
	/** Efficiency of vertex 'v' when producing for supply this vertex. */
	double getEfficiency(Vertex v);
	
}
