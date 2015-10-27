package graph;

/** Necessary edge attributes for solving the demand problem.  */
public interface Edge {

	/** Amount of units lost during transmission across the edge. */
	double getLoss();
	
	/** Part of demand this edge is responsible for supplying. */
	double getShare();
}
