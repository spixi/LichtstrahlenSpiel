package de.bwvaachen.beamoflightgame.helper;

public class Pair<L,R> {
	public Pair(final L l, final R r){
		this.left  = l;
		this.right = r;
	}
	public final L left;
	public final R right;

	public String toString() {
		return String.format("{ %s; %s }", left.toString(), right.toString());
	}
	
	public int hashCode() {
		//97 is a nice prime number
		//without this multiplication hashCode() would always return 0 if left == right
		return left.hashCode() ^ (right.hashCode()*97);
	}
	
	public boolean equals(Pair<Object,Object> aPair) {
		return this.left.equals(aPair.left) && this.right.equals(aPair.right);
	}
}
