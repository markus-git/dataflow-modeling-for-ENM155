package graph;

public interface Vertex {

	String getLabel();
	
	double getDemand();
	
	void   addDemand(double x);
	
	double getEfficiency(Vertex v);
	
}
