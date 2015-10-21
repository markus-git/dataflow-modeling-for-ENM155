
public class Main {
	
	public static void main(String[] args) {
		
		// Example network		
		Node n1 = new Node();
		Node n2 = new Node();
		Node n3 = new Node();
		Node n4 = new Node();
		
		Edge e2to1 = new Edge(0.05, 0.65, 0.7, n2);
		Edge e3to1 = new Edge(0.05, 0.35, 0.7, n3);
		Edge e3to2 = new Edge(0,    0.10, 2.0, n3);
		Edge e4to2 = new Edge(0.01, 0.90, 0.8, n4);
		Edge e4to3 = new Edge(0.01, 1.0,  0.8, n4);
		
		n1.connect(e2to1);
		n1.connect(e3to1);
		n2.connect(e3to2);
		n2.connect(e4to2);
		n3.connect(e4to3);
		
		// ...
		n1.init(100);
		n1.demand();
		
		System.out.println("1: " + n1.read());
		System.out.println("2: " + n2.read());
		System.out.println("3: " + n3.read());
		System.out.println("4: " + n4.read());
	}

}
