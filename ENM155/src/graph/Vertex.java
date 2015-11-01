package graph;

/** Necessary vertex attributes for solving the demand problem. */
public interface Vertex {

	/** Name assigned to vertex. */
	String getLabel();
	
	/** Current output of vertex. */
	double getOutput();
	
	/** Current input of vertex. */
	double getInput();
	
	/** Request additional supply from this vertex, increasing its output. */
	void demandInput(double x);
	
	/** Sets this vertex input to match its output. (If it took a value we would have to worry about precision) */
	void satisfyOutput();
	
	/** Efficiency of vertex 'v' when producing for supply this vertex. */
	double getEfficiency(Vertex v);
	
}
