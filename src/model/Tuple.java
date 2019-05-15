package model;

/**
 * 
 * @author Mark Bzomowski
 * 
 * A tuple class, for representing one particular spot on a 2D
 * int array, so we can hash them to a hashset.
 *
 */
public class Tuple {
	private int x;
	private int y;
	
	public Tuple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	/**
	 * I had to write an equals method for this class so it would work in
	 * a hashSet
	 */
	@Override
	public boolean equals(Object t) {
		if (!(t instanceof Tuple)) {
			return false;
		}
		if (t == this) {
			return true;
		}
		return ((Tuple)t).getX() == this.x && ((Tuple)t).getY() == this.y;
	}
	
	/**
	 * I had to write a hashCode method for this class so it would work in a hashSet
	 */
	public int hashCode() {
		return this.getX() * 3 + this.getY() * 5 + 7;
	}

}
