package model;

import java.util.Random;

import enums.ID;
import javafx.scene.image.Image;
import view.map.WorldView;

/**
 * 
 * @author mbzomowski (Mark Bzomowski)
 *
 *	This class handles procedurally generating all of the screens on the map,
 *	as well as randomizing how each screen appears.
 */
public class WorldGenerator {

	static WorldView viewPointer;

	// Images for enemies
	private static Image chargerImage = new Image("file:src/images/triceratopsSpriteSheet.png");
	private static Image stalkerImage = new Image("file:src/images/trexSpriteSheet.png");
	private static Image flierImage = new Image("file:src/images/pteradactylSpriteSheet.png");
	
	
	private static Random rng = new Random();

	public static WorldModel proceduralGeneration(WorldView view) {
		viewPointer = view;

		// All Tiles are as follows:
		
		// Dirt Tiles
		Tile dirt = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 0, 8, 1, 1);
		
		// Tree Tiles
		Tile trees = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 0, 8, 0, 1);
		
		// Cliff Tiles for the starting screen
		Tile[][] cliff = new Tile[3][3];
		for (int i = 2; i < 5; i++) {
			for (int j = 5; j < 8; j++) {
				cliff[i - 2][j - 5] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, j, 1,
						i, 1);
			}
		}
		
		// Cave entrance tiles for the screen just south of the cave
		Tile[][] cave_entrance = new Tile[4][12];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				cave_entrance[i][j] = dirt;
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				cave_entrance[i][j] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 3 + j,
						1, 3 + i, 1);
			}
		}
		cave_entrance[0][5] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 2, 1, 7, 1);
		cave_entrance[0][6] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 3, 1, 7, 1);
		cave_entrance[1][5] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 2, 1, 8, 1);
		cave_entrance[1][6] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 3, 1, 8, 1);
		for (int i = 0; i < 2; i++) {
			for (int j = 7; j < 10; j++) {
				cave_entrance[i][j] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, j - 2,
						1, 3 + i, 1);
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 10; j < 12; j++) {
				cave_entrance[i][j] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, j - 7,
						1, 3 + i, 1);
			}
		}
		cave_entrance[2][4] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 0, 1, 9, 1);
		cave_entrance[3][4] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 0, 1, 11, 1);
		cave_entrance[2][5] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 1, 1, 9, 1);
		cave_entrance[3][5] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 1, 1, 11, 1);
		cave_entrance[2][6] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 1, 1, 9, 1);
		cave_entrance[3][6] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 1, 1, 11, 1);
		cave_entrance[2][7] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 2, 1, 9, 1);
		cave_entrance[3][7] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", true, 2, 1, 11, 1);

		for (int i = 8; i < 11; i++) {
			cave_entrance[2][i] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 1, 1, 4,
					1);
		}
		for (int i = 1; i < 4; i++) {
			cave_entrance[2][i] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 1, 1, 4,
					1);
		}
		for (int i = 1; i < 4; i++) {
			cave_entrance[3][i] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 1, 1, 6,
					1);
		}
		for (int i = 8; i < 11; i++) {
			cave_entrance[3][i] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 1, 1, 6,
					1);
		}
		cave_entrance[2][0] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 0, 1, 4, 1);
		cave_entrance[2][11] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 2, 1, 4, 1);
		cave_entrance[3][0] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 0, 1, 6, 1);
		cave_entrance[3][11] = new Tile("file:src/images/tf_jungle/rpgmaker/RMMV/tf_jungle_a5.png", false, 2, 1, 6, 1);

		// Cave Tiles
		Tile cave[][] = new Tile[12][12];
		cave[0][0] = new Tile("file:src/images/cave.png", false, 0, 1, 0, 1);
		cave[0][11] = new Tile("file:src/images/cave.png", false, 2, 1, 0, 1);
		cave[11][0] = new Tile("file:src/images/cave.png", false, 0, 1, 2, 1);
		cave[11][11] = new Tile("file:src/images/cave.png", false, 2, 1, 2, 1);
		for (int i = 1; i < 11; i++) {
			cave[0][i] = new Tile("file:src/images/cave.png", false, 1, 1, 0, 1);
			cave[11][i] = new Tile("file:src/images/cave.png", false, 1, 1, 2, 1);
			cave[i][0] = new Tile("file:src/images/cave.png", false, 0, 1, 1, 1);
			cave[i][11] = new Tile("file:src/images/cave.png", false, 2, 1, 1, 1);
		}
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 11; j++) {
				cave[i][j] = new Tile("file:src/images/cave.png", true, 0, 2, 9, 2);
			}
		}
		cave[11][5] = new Tile("file:src/images/cave.png", true, 0, 2, 9, 2);
		cave[11][6] = new Tile("file:src/images/cave.png", true, 0, 2, 9, 2);
		
		// Water, Dirt, and Tree tiles for the final screen
		// These must be from the same image file, otherwise it wont work
		Tile final_water = new Tile("file:src/images/alltiles.png", false, 0, 1, 0, 1);
		Tile final_dirt = new Tile("file:src/images/alltiles.png", true, 4, 2, 1, 3);
		Tile final_trees = new Tile("file:src/images/alltiles.png", false, 0, 2, 1, 3);

		// Generates an empty array for the 
		ScreenMap[][] DUMMY_WORLD = new ScreenMap[8][8];

		// This conducts the random generation of the world map
		// It will lay down 10 "normal" screens, then connect them in a minimum
		// spanning tree. One is randomly selected as the starting screen, then
		// a BFS is used to find the furthest screen from that one, and that is
		// the ending screen with the river/uggh-ina
		MapGenerator generatedMap = new MapGenerator();
		generatedMap.populateWorldMap();
		generatedMap.populateConnectors();
		generatedMap.setBossAndStartScreen();
		generatedMap.placeCaveScreen();

		ScreenMap currentScreen;
		boolean[] availableConnections = new boolean[4];

		boolean populatedStick = false;

		// Iterates threw the newly generated world map
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				availableConnections[0] = false;
				availableConnections[1] = false;
				availableConnections[2] = false;
				availableConnections[3] = false;

				currentScreen = new ScreenMap();

				// Only generates screens for worldMap locations that aren't blank
				if (generatedMap.getWorldMap()[i][j] != 0) {
					int mapType = generatedMap.getWorldMap()[i][j];

					// Three possible enemy populations
					// Always put at least one stick in the game
					if (mapType == 1) {
						if (!populatedStick) {
							addEnemies3(currentScreen);
							populatedStick = true;
						} else {
							int x = rng.nextInt(3);
							if (x == 1) {
								addEnemies1(currentScreen);
							} else if (x == 2) {
								addEnemies2(currentScreen);
							} else {
								addEnemies3(currentScreen);
							}
						}

					} else if (mapType == 3) {
						populateCave(currentScreen);
					}
					// Checks to see if the current screen is connected to any other screens
					// This will create an opening to that screen
					for (int m = Math.max(i - 1, 0); m <= Math.min(i + 1, 7); m++) {
						for (int n = Math.max(j - 1, 0); n <= Math.min(j + 1, 7); n++) {
							if ((m == i && n == j) || (m != i && n != j)) {
								continue;
							}
							if (m < i && generatedMap.getWorldMap()[m][n] != 0
									&& generatedMap.getWorldMap()[m][n] != 3) {
								availableConnections[0] = true;
								continue;
							}
							if (n > j && generatedMap.getWorldMap()[m][n] != 0
									&& generatedMap.getWorldMap()[m][n] != 3) {
								availableConnections[1] = true;
								continue;
							}
							if (m > i && generatedMap.getWorldMap()[m][n] != 0
									&& generatedMap.getWorldMap()[m][n] != 3) {
								availableConnections[2] = true;
								continue;
							}
							if (n < j && generatedMap.getWorldMap()[m][n] != 0
									&& generatedMap.getWorldMap()[m][n] != 3) {
								availableConnections[3] = true;
								continue;
							}
						}
					}
					int[][] currentScreenMap = new int[12][12];
					
					// Generates the current screen
					currentScreenMap = generateScreen(mapType, availableConnections[0], availableConnections[1],
							availableConnections[2], availableConnections[3],
							bordersCaveToNorth(generatedMap.getWorldMap(), i, j));
					for (int m = 0; m < 12; m++) {
						for (int n = 0; n < 12; n++) {
							
							// This sets the tiles for the final screen, with the river
							if (mapType == 8) {
								if (currentScreenMap[m][n] == 0) {
									currentScreen.setTile(final_dirt, n, m);
								}
								else if (currentScreenMap[m][n] == 1) {
									currentScreen.setTile(final_trees, n, m);
								}
								else if (currentScreenMap[m][n] == 9) {
									currentScreen.setTile(final_water, n, m);
								}
								continue;
							}
							
							// Sets tile as dirt
							if (currentScreenMap[m][n] == 0) {
								currentScreen.setTile(dirt, n, m);
							} 
							// Sets tile as trees
							else if (currentScreenMap[m][n] == 1) {
								currentScreen.setTile(trees, n, m);
							}

							// Sets tiles as cliff on the starting screen
							else if (currentScreenMap[m][n] == 2) {
								currentScreen.setTile(cliff[m][n % 3], n, m);
							}
							// Sets tiles as cave entrance
							else if (currentScreenMap[m][n] == 4) {
								currentScreen.setTile(cave_entrance[m][n], n, m);
							}
							// Sets tiles as cave
							else if (currentScreenMap[m][n] == 5) {
								currentScreen.setTile(cave[m][n], n, m);
							}
						}
					}
				}

				// Places the screen at our current position on the world map
				DUMMY_WORLD[j][i] = currentScreen;
			}
		}

		// Make a 2D array to hold the dummy screen so we can make a WorldModel object
		// with it
		WorldModel thisModel = new WorldModel(DUMMY_WORLD);
		Tuple startingScreen = generatedMap.getStartingScreen();
		thisModel.setScreen(startingScreen.getX(), startingScreen.getY()); // Starting screen for testing.
		thisModel.addObserver(view);
		//generatedMap.printWorldMap(); We have a minimap now!
		view.setWorldMap(generatedMap.getWorldMap());
		return thisModel;

	}

	
	// Charger city
	private static void addEnemies1(ScreenMap screen) {
		// Add between 3 - 6 chargers
		for (int i = 0; i < rng.nextInt(3) + 3; i++) {
			Charger tri;
			if (i % 2 == 0) {
				tri = new Charger(chargerImage, 1, 1 + (i * 2), ID.Enemy, viewPointer);
				tri.setFacingRight(true);

			} else {
				tri = new Charger(chargerImage, 10, 1 + (i * 2), ID.Enemy, viewPointer);
				tri.setFacingRight(false);
			}
			tri.setSpeed(4);
			screen.objects.add(tri);
		}

		// Add 1-2 stalkers
		Stalker s1 = new Stalker(stalkerImage, 4, 4, ID.Enemy, viewPointer);
		s1.setSpeed(1);
		screen.objects.add(s1);

		if (rng.nextInt(2) == 0) {
			Stalker s = new Stalker(stalkerImage, 6, 6, ID.Enemy, viewPointer);
			s.setSpeed(1);
			screen.objects.add(s);
		}

	}

	// Stalker city, plus some chargers
	private static void addEnemies2(ScreenMap screen) {
		Stalker s1 = new Stalker(stalkerImage, 1, 10, ID.Enemy, viewPointer);
		Stalker s2 = new Stalker(stalkerImage, 10, 1, ID.Enemy, viewPointer);
		Stalker s3 = new Stalker(stalkerImage, 1, 1, ID.Enemy, viewPointer);
		Stalker s4 = new Stalker(stalkerImage, 10, 10, ID.Enemy, viewPointer);
		s1.setSpeed(1);
		s2.setSpeed(1);
		s3.setSpeed(2);
		s4.setSpeed(1);
		screen.objects.add(s1);
		screen.objects.add(s2);
		screen.objects.add(s3);
		screen.objects.add(s4);

		// Add 1-2 chargers
		for (int i = 0; i < rng.nextInt(2) + 1; i++) {
			Charger tri;
			if (i % 2 == 0) {
				tri = new Charger(chargerImage, 1, 5 + (i * 2), ID.Enemy, viewPointer);
				tri.setFacingRight(true);

			} else {
				tri = new Charger(chargerImage, 10, 5 + (i * 2), ID.Enemy, viewPointer);
				tri.setFacingRight(false);
			}
			tri.setSpeed(4);
			screen.objects.add(tri);
		}
	}

	// A nice mix (the hardest mix, also the only one generated with sticks in >:D )
	private static void addEnemies3(ScreenMap screen) {
		// Add 1-2 chargers
		for (int i = 0; i < rng.nextInt(2) + 1; i++) {
			Charger tri;
			if (i % 2 == 0) {
				tri = new Charger(chargerImage, 1, 5 + (i * 2), ID.Enemy, viewPointer);
				tri.setFacingRight(true);

			} else {
				tri = new Charger(chargerImage, 10, 5 + (i * 2), ID.Enemy, viewPointer);
				tri.setFacingRight(false);
			}
			tri.setSpeed(4);
			screen.objects.add(tri);
		}

		// Add 1 stick
		Stick s = new Stick(6, 6, ID.Collectable, viewPointer);
		screen.objects.add(s);

		// Add 2 - 3 stalkers
		for (int i = 0; i < rng.nextInt(2) + 2; i++) {
			Stalker stalker;
			int x = rng.nextInt(5);
			if (i % 2 == 0) {
				stalker = new Stalker(stalkerImage, 2 + x * 2, 1 + i + x, ID.Enemy, viewPointer);

			} else {
				stalker = new Stalker(stalkerImage, 5 + x, 1 + i + x, ID.Enemy, viewPointer);
			}
			stalker.setSpeed(1);
			screen.objects.add(stalker);
		}

		while (screen.objects.size() < 8) {
			int x = rng.nextInt(10) + 1;
			int y = rng.nextInt(10) + 1;
			Flier f = new Flier(flierImage, x, y, ID.Enemy, viewPointer);
			screen.objects.add(f);
		}
	}

	/**
	 * 
	 * @param screen	A screenMap
	 * 
	 * This will populate the cave with fliers, and the vine
	 */
	private static void populateCave(ScreenMap screen) {
		Vine v = new Vine(6, 6, ID.Collectable, viewPointer);
		screen.objects.add(v);

		Flier f1 = new Flier(flierImage, 1, 1, ID.Enemy, viewPointer);
		Flier f2 = new Flier(flierImage, 1, 10, ID.Enemy, viewPointer);
		Flier f3 = new Flier(flierImage, 10, 1, ID.Enemy, viewPointer);
		Flier f4 = new Flier(flierImage, 10, 10, ID.Enemy, viewPointer);
		screen.objects.add(f1);
		screen.objects.add(f2);
		screen.objects.add(f3);
		screen.objects.add(f4);

		// Add 1-2 more fliers
		for (int i = 0; i < rng.nextInt(2) + 1; i++) {
			Flier f = new Flier(flierImage, 4 + i, 3 + (i * 2), ID.Enemy, viewPointer);
			screen.objects.add(f);
		}

	}

	/**
	 * 
	 * @param screenType		The type of screen to be generated
	 * 							1:	Normal screen w/ Enemies
	 * 							2:  Transition screen w/ no enemies
	 * 							3:  Cave screen
	 * 							7:  Starting Screen
	 * 							8:  Final screen
	 * @param northConnected	Boolean value, whether or not this is connected by a screen to the north
	 * @param eastConnected		Boolean value, whether or not this is connected by a screen to the east
	 * @param southConnected	Boolean value, whether or not this is connected by a screen to the south
	 * @param westConnected		Boolean value, whether or not this is connected by a screen to the west
	 * @param bordersCave		Boolean value, whether or not this is connected to the cave to the north
	 * @return					The generated screenMap
	 */
	private static int[][] generateScreen(int screenType, boolean northConnected, boolean eastConnected,
			boolean southConnected, boolean westConnected, boolean bordersCave) {
		int[][] screen = new int[12][12];

		int type = screenType == 1 || screenType == 7 || screenType == 8 ? 1 : 2;

		// Starts with a screen filled with trees
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				screen[i][j] = 1;
			}
		}

		// If the screen to be made is the cave, fill with cave tiles
		if (screenType == 3) {
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 12; j++) {
					screen[i][j] = 5;
				}
			}
			return screen;
		}

		// If this is a normals screen, remove most of the trees, replace with dirt
		if (type == 1) {
			for (int i = 1; i < 11; i++) {
				for (int j = 1; j < 11; j++) {
					screen[i][j] = 0;
				}
			}
		}

		// If this is a transition screen, replace with less dirt
		else if (type == 2) {
			for (int i = 4; i < 8; i++) {
				for (int j = 4; j < 8; j++) {
					screen[i][j] = 0;
				}
			}
		}
		
		// If this is the starting screen, mark tiles for the cliff
		if (screenType == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 12; j++) {
					screen[i][j] = 2;
				}
			}
			for (int j = 0; j < 12; j++) {
				screen[3][j] = 1;
			}
		}

		// Clear path to the north
		if (northConnected) {
			for (int i = 0; i < 4; i++) {
				for (int j = 4; j < 8; j++) {
					screen[i][j] = 0;
				}
			}
		}

		// Clear path to the east
		if (eastConnected) {
			for (int i = 4; i < 8; i++) {
				for (int j = 8; j < 12; j++) {
					screen[i][j] = 0;
				}
			}
		}

		// Clear path to the south
		if (southConnected) {
			for (int i = 8; i < 12; i++) {
				for (int j = 4; j < 8; j++) {
					screen[i][j] = 0;
				}
			}
		}

		// Clear path to the west
		if (westConnected) {
			for (int i = 4; i < 8; i++) {
				for (int j = 0; j < 4; j++) {
					screen[i][j] = 0;
				}
			}
		}

		// If this screen borders the cave to the north, set up the cave entrance
		if (bordersCave) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 12; j++) {
					screen[i][j] = 4;
				}
			}
		}
		
		// If this is the final screen, mark tiles for river
		if (screenType == 8) {
			for (int i = 4; i < 8; i++) {
				for (int j = 4; j < 8; j++) {
					screen[i][j] = 9;
				}
			}
			if (!northConnected && !southConnected) {
				for (int i = 0; i < 4; i++) {
					for (int j = 4; j < 8; j++) {
						screen[i][j] = 9;
					}
				}
				for (int i = 8; i < 12; i++) {
					for (int j = 4; j < 8; j++) {
						screen[i][j] = 9;
					}
				}
			}
			if (!westConnected && !eastConnected) {
				for (int i = 4; i < 8; i++) {
					for (int j = 0; j < 4; j++) {
						screen[i][j] = 9;
					}
				}
				for (int i = 4; i < 8; i++) {
					for (int j = 8; j < 12; j++) {
						screen[i][j] = 9;
					}
				}
			}
		}
		return screen;
	}

	/**
	 * 
	 * @param map	A given world map, as an int[][]
	 * @param y		The y location of the screen
	 * @param x		The x location of the screen
	 * @return		Returns a boolean, whether or not this screen borders the 
	 * 				cave to the north
	 */
	private static boolean bordersCaveToNorth(int[][] map, int y, int x) {
		if (y == 0)
			return false;
		if (map[y - 1][x] == 3) {
			return true;
		}
		return false;
	}
}
