package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 
 * @author Mark Bzomowsk (mbzomowski)
 * @author Matt Burns
 * 
 * A single screen of the game, consisting of a 12-by-12 array of tiles
 *
 */
public class ScreenMap {
	// Dimensions of a single screen, measured in tiles
	public static final int SCREEN_HEIGHT = 12;
	public static final int SCREEN_WIDTH = 12;
	
	// Measurements, in pixels, of how big to draw each tile 
	public static final int RENDER_WIDTH = 48;
	public static final int RENDER_HEIGHT = 48;
	
	private String backgroundMusic;
	public HashMap<Integer[], String> ExitCoordinates; //each Integer[0],Integer[1] is x,y for an exit, links to String ScreenID
	public HashMap<String, Integer[]> EntranceCoordinates; //String fromScreen maps to x,y for entrance
	
	private Tile[][] tiles;
	public ArrayList<GameObject> objects;
	public GameObject player;
	public GameObject spear;
	
	public ScreenMap() {
		tiles = new Tile[SCREEN_HEIGHT][SCREEN_WIDTH];
		objects = new ArrayList<GameObject>();
		
		// Pre-populate screen with empty, impassable tiles
		for (int i = 0; i < SCREEN_HEIGHT; i++) {
			for (int j = 0; j < SCREEN_WIDTH; j++) {
				tiles[i][j] = new Tile("", false, -1, -1, -1, -1);
				
			}
		}
	}
	
	public void addPlayer(GameObject p) {
		this.objects.add(p);
		this.player = p;
	}
	
	public void addSpear(GameObject s) {
		this.objects.add(s);
		this.spear = s;
	}
	
	/**
	 * 
	 * @param obj		A ScreenTile to be placed in a screen
	 * @param col		The column of the object
	 * @param row		The row of the object
	 */
	public void setTile(Tile t, int i, int j) {
		tiles[i][j] = t;
	}
	
	/**
	 * 
	 * @param col		The column of the object
	 * @param row		The row of the object
	 * @return			The ScreenTile at (row, col)
	 */
	public Tile tileAt(int i, int j) {
		return tiles[i][j];
	}
	
	/**
	 * This method returns the background music that should be used for this screen.
	 * By default, it's the Menu music. Other screens should update the constructor such
	 * that backgroundMusic gets set to another value
	 * @return       The String for which song should be playing when this screen is loaded
	 */
	public String getBackgroundMusic() {
		return backgroundMusic;
	}
	
	/**
	 * 
	 * @param musicPath		A music file
	 * 
	 * Sets the background music for a particular screen
	 */
	public void setBackgroundMusic(String musicPath) {
		backgroundMusic = musicPath;
	}
	
	/**
	 * Controls enemies on each screen
	 */
	public void controlEnemies() {
		for (GameObject o : this.objects) {
			if (o instanceof Enemy) {
				((Enemy) o).attack(this);
			}
		}
	}
	
}