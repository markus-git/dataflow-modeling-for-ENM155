package graph;

public interface Vertex {

	double getDemand();
	
	void   addDemand(double x);
	
	double getEfficiency(Vertex v);
	
}
