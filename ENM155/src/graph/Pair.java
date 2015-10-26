package graph;

public class Pair<U, V> {

	private U first;
	private V second;
	
	public Pair(U first, V second) {
		this.first  = first;
		this.second = second;
	}
	
	public U fst() {
		return this.first;
	}
	
	public V snd() {
		return this.second;
	}
}
