package graph;

/** Necessary edge attributes for solving the demand problem.  */
public interface Edge {

	/** Part of demand this edge is responsible for supplying. */
	double getShare();
}
