package model;

/**
 *  A class to hold data representing one tile of one map screen.
 * 
 * @author Matt Burns
 *
 */


public class Tile {
	
	private String imgPath;
	private boolean passable;

	private int startCol;
	private int colRange;
	private int startRow;
	private int rowRange;
	
	
	public Tile(String path, boolean p, int col, int colRng, int row, int rowRng) {
		this.imgPath = path;
		this.passable = p;
		this.startCol = col;
		this.colRange = colRng;
		this.startRow = row;
		this.rowRange = rowRng;
	}
	
	public String imgPath() {
		return imgPath;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public int startCol() {
		return startCol;
	}
	
	public int colRange() {
		return colRange;
	}
	
	public int startRow() {
		return startRow;
	}
	
	public int rowRange() {
		return rowRange;
	}

}
