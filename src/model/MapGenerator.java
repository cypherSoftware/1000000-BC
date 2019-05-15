package model;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * @author Mark Bzomowski
 * 
 *         This class will generate a procedurally generated WORLD_SIZE x
 *         WORLD_SIZE array of integers, representing the procedurally generated
 *         world map. There will be NUM_SCREENS number of main screens
 *         (represented by 1's in the array) on which there will be enemies,
 *         which will be scattered around the map. All of these will be
 *         connected via a minimum spanning tree of transition screens
 *         (represented by 2's in the array).
 *
 */
public class MapGenerator {
	// The size of the world. If WORLD_SIZE = 8, the world will be an array of 8x8
	// ScreenMap objects
	public static final int WORLD_SIZE = 8;

	// The number of main screens that will appear on the world map
	private final int NUM_SCREENS = 10;

	private int[][] worldMap = new int[WORLD_SIZE][WORLD_SIZE];
	private Tuple startingScreen;
	private HashSet<Tuple> furthestPointVisited = new HashSet<>();
	private LinkedList<Tuple> q = new LinkedList<>();
	
	/**
	 * This method will add NUM_SCREENS number of main screens onto the worldMap int
	 * array, randomly placed
	 */
	public void populateWorldMap() {
		for (int i = 0; i < WORLD_SIZE; i++) {
			for (int j = 0; j < WORLD_SIZE; j++) {
				worldMap[i][j] = 0;
			}
		}

		Random rand = new Random();
		Date date = new Date();
		// Seeded with the current time, for true randomness
		rand.setSeed(date.getTime());
		int rand_x = rand.nextInt(WORLD_SIZE);
		int rand_y = rand.nextInt(WORLD_SIZE);

		// Adds NUM_SCREENS main screens to the world map
		for (int i = 0; i < NUM_SCREENS; i++) {
			worldMap[rand_y][rand_x] = 1;
			rand_y = rand.nextInt(WORLD_SIZE);
			rand_x = rand.nextInt(WORLD_SIZE);
			while (worldMap[rand_y][rand_x] == 1) {
				rand_y = rand.nextInt(WORLD_SIZE);
				rand_x = rand.nextInt(WORLD_SIZE);
			}
		}
	}

	/**
	 * Adds 2's to the worldMap, connecting all the main screens together in a
	 * minimum spanning tree
	 */
	public void populateConnectors() {
		HashSet<Tuple> visited = new HashSet<>();
		HashSet<Tuple> unvisited = new HashSet<>();
		Tuple starting = null;

		// Add all main screens to unvisited. One screen will be added to visited,
		// and that will be the starting screen
		for (int i = 0; i < WORLD_SIZE; i++) {
			for (int j = 0; j < WORLD_SIZE; j++) {
				if (worldMap[i][j] == 1) {
					if (starting == null) {
						starting = new Tuple(j, i);
						visited.add(starting);
						startingScreen = starting;
						continue;
					}
					unvisited.add(new Tuple(j, i));
				}
			}
		}

		// If the starting screen has any immediately bordering screens, add those
		// to visited as well.
		addNeighborsToVisited(visited, unvisited, starting);

		Tuple start = null;
		Tuple end = null;

		// One by one, screens are added to the network of screens via connecting
		// pathways
		while (unvisited.isEmpty() == false) {
			int distance = Integer.MAX_VALUE;
			for (Tuple s : visited) {
				if (start == null) {
					start = s;
				}
				for (Tuple e : unvisited) {
					if (distance > distance(s, e)) {
						distance = distance(s, e);
						start = s;
						end = e;
					}
				}
			}
			generatePath(start, end);
			unvisited.remove(end);
			visited.add(end);
			addNeighborsToVisited(visited, unvisited, end);
		}
	}

	/**
	 * 
	 * @param s The starting screen
	 * @param e The ending screen
	 * 
	 *          This method will generate a randomized path of 2's from s to e on
	 *          the world map
	 */
	public void generatePath(Tuple s, Tuple e) {
		Random rand = new Random();
		int x = s.getX();
		int y = s.getY();
		int d_x = x < e.getX() ? 1 : -1;
		int d_y = y < e.getY() ? 1 : -1;

		while (x != e.getX() && y != e.getY()) {
			int x_or_y = rand.nextInt(2);
			if (x_or_y == 0) {
				x += d_x;
				worldMap[y][x] = 2;
			} else {
				y += d_y;
				worldMap[y][x] = 2;
			}
		}
		if (y == e.getY()) {
			while (x != e.getX()) {
				if (worldMap[y][x] != 1) {
					worldMap[y][x] = 2;
				}
				x += d_x;
			}
		}
		if (x == e.getX()) {
			while (y != e.getY()) {
				if (worldMap[y][x] != 1) {
					worldMap[y][x] = 2;
				}
				y += d_y;
			}
		}
	}

	/**
	 * 
	 * @param visited   All nodes which have been visited
	 * @param unvisited All nodes which have been unvisited
	 * @param t         The node we're checking for adjacent nodes
	 * 
	 *                  This method will add all nodes adjacent to the given node to
	 *                  the HashSet visited This will make it easier to generate a
	 *                  minimum spanning tree of paths between all the main
	 *                  screens/nodes
	 */
	private void addNeighborsToVisited(HashSet<Tuple> visited, HashSet<Tuple> unvisited, Tuple t) {
		for (int i = Math.max(t.getY() - 1, 0); i <= Math.min(t.getY() + 1, WORLD_SIZE - 1); i++) {
			for (int j = Math.max(t.getX() - 1, 0); j <= Math.min(t.getX() + 1, WORLD_SIZE - 1); j++) {
				if ((i == t.getY() && j == t.getX()) || (i != t.getY() && j != t.getX())) {
					continue;
				}
				if (worldMap[i][j] == 1 && !visited.contains(new Tuple(j, i))) {
					Tuple current = new Tuple(j, i);
					visited.add(current);
					unvisited.remove(current);
					addNeighborsToVisited(visited, unvisited, current);
				}
			}
		}
	}

	/**
	 * 
	 * @param a Starting node
	 * @param b Ending node
	 * @return The distance between the two
	 * 
	 *         Returns the distance between two spots on the worldMap This does not
	 *         generate distance from the pythagorean thm, but from adding the
	 *         differences in the x values to the y values, as that is the only way
	 *         you can move from screen to screen (up, down, left, or right, no
	 *         diagonal)
	 */
	private int distance(Tuple a, Tuple b) {
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}

	/**
	 * 
	 * @return Returns the worldMap
	 */
	public int[][] getWorldMap() {
		return worldMap;
	}

	/**
	 * 
	 * @param m Sets the worldMap to a given int array
	 * 
	 *          Only for testing purposes
	 */
	public void setWorldMap(int[][] m) {
		this.worldMap = m;
	}

	/**
	 * Only for testing purposes
	 */
	public void printWorldMap() {
		for (int i = 0; i < WORLD_SIZE; i++) {
			for (int j = 0; j < WORLD_SIZE; j++) {
				System.out.print(worldMap[i][j] + " ");
			}
			System.out.println();
		}
	}
	

	/**
	 * 
	 * @return		The starting screen as a Tuple
	 */
	public Tuple getStartingScreen() {
		return startingScreen;
	}
	
	/**
	 * 
	 * @param s		The starting point, given as a Tuple
	 * @param map	The map layout, given as an int[][]
	 * @return		Returns the farthest point, given as a Tuple
	 * 
	 * This will find the furthest point from the starting screen, in order
	 * to place the final screen there. A BFS is used in order to accomplish
	 * this.
	 */
	public Tuple findFurthestPoint(Tuple s, int[][] map) {
		q.addLast(s);
		furthestPointVisited.add(s);
		Tuple current = null;
		
		while (!q.isEmpty()) {
			current = q.removeFirst();
			
			for (int i = Math.max(0, current.getY() - 1); i <= Math.min(WORLD_SIZE - 1, current.getY() + 1); i++) {
				for (int j = Math.max(0, current.getX() - 1); j <= Math.min(WORLD_SIZE - 1, current.getX() + 1); j++) {
					if ((i != current.getY() && j != current.getX()) || (i == current.getY() && j == current.getX())) {
						continue;
					}
					if (map[i][j] != 0 && !furthestPointVisited.contains(new Tuple(j, i))) {
						q.addLast(new Tuple(j, i));
						furthestPointVisited.add(new Tuple(j, i));
					}
				}
			}
		}
		return current;
	}
	
	/**
	 * Adds the boss screen and starting screen to the current map layout
	 */
	public void setBossAndStartScreen() {
		Tuple boss = findFurthestPoint(startingScreen, worldMap);
		worldMap[boss.getY()][boss.getX()] = 8;
		worldMap[startingScreen.getY()][startingScreen.getX()] = 7;
	}
	
	/**
	 * Places a cave screen to the north of some random screen,
	 * only accessible from that screen.
	 */
	public void placeCaveScreen() {
		for (int i = 1; i < WORLD_SIZE; i++) {
			for (int j = 0; j < WORLD_SIZE; j++) {
				if ((worldMap[i][j] == 1 || worldMap[i][j] == 2) && worldMap[i-1][j] == 0) {
					worldMap[i-1][j] = 3;
					return;
				}
			}
		}
	}

}