package graph;

/** Necessary vertex attributes for solving the demand problem. */
public interface Vertex {

	/** Name assigned to vertex. */
	String getLabel();
	
	/** Current demand requested by vertex. */
	double getDemand();
	
	/** Request additional supply from this vertex, increasing its demand. */
	void addDemand(double x);
	
	/** Efficiency of vertex 'v' when producing for supply this vertex. */
	double getEfficiency(Vertex v);
	
}
