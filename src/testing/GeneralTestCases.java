package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.lang.ExceptionInInitializerError;

import controller.*;
import enums.ID;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import model.*;
import view.map.WorldView;

public class GeneralTestCases {
	@Test
	public void WorldControllerTest() {
		//The world generator cannot take a World Model in test as seen in the Crash and Burn tests
		//This is because a model fails to instantiate without the GUI. 
		//So the only non-gui dependent tests we can do on it are those that
		//take null for the model.
		WorldController worldController = new WorldController(null);
		worldController.startClock();
		//This should throw a null pointer exception because model is null
		assertThrows(NullPointerException.class, () -> {worldController.getScreen();}, "Se puso en ceros y nadie se preocupo por ella, que triste.");
		
		//For the same reason, getCurrentX and getCurrentY will throw errors.
		
		Timeline clock = worldController.getGameClock();
		assertTrue(clock instanceof Timeline);
		//That leaves 64 instructions uncovered, which are all part of the clock handler or methods dealing with the model which can't not be null.
		//Thus the 57 instructions that are covered are the only ones that aren't GUI dependent, so that's 100% non-GUI dependent code coverage.
		
	}
	
	/**
	 * This test tests the ScreenMap object.
	 * Note that the condition
	 * if (o instanceof Enemy) {
	 *			((Enemy) o).attack(this);
	 * 	}
	 * can only be true under the circumstance that we have
	 * an instantiated Enemy object. Enemies, along with all GameObjects,
	 * extend ImageView, so they can't be instantiated without a view. Therefore,
	 * 100% of the non-gui code has been tested here.
	 */
	@Test
	public void ScreenMapTest() {
		ScreenMap map = new ScreenMap();
		GameObject arbitraryObject = null;
		map.addPlayer(arbitraryObject);
		Tile tile = map.tileAt(3, 5);
		map.setTile(tile, 4, 4);
		map.setBackgroundMusic("Garbage.mp3");
		assertEquals(map.getBackgroundMusic(), "Garbage.mp3");
		map.controlEnemies();
		Enemy enemy = null;
		map.objects.add(enemy);
		map.controlEnemies();
		Image img = null;
		WorldView view = null;
		assertThrows(ExceptionInInitializerError.class, () -> {Enemy enemy2 = new Flier(img, 4, 8, ID.Enemy, view);}, "ImageView objects thrown ExceptionInInitializerError when initialized without a screen to be viewed on. Sad, right?");
		
	}
	
	/**
	 * This test tests the Cheat Box object.
	 * This object can be instantiated... But that's not how it's used at all. The
	 * other methods throw an error.
	 * Therefore, this is 100% branch coverage for this class' non-GUI dependent code.
	 */
	@Test
	public void CheatBoxTest() {
		CheatBox cheatBox = new CheatBox();
		//The displayCheatBox unavoidably throws an error, so that's in Crash and Burn
		
	}
	
	/**
	 * MapGenerator tests. These get nearly 100% code coverage, just a fraction of a percent may
	 * or may not be covered depending on how lucky we are with random numbers. I could have made this loop 100x, but
	 * taking an extra 5 minutes to go up 1% didn't seem worth it.
	 */
	@Test
	public void MapGeneratorTests() {
		MapGenerator generator = new MapGenerator();
		int[][] emptyMapArray = generator.getWorldMap();
		//Should be all zeroes
		assertTrue(emptyMapArray.length == MapGenerator.WORLD_SIZE);
		for (int i = 0; i < MapGenerator.WORLD_SIZE; i++) {
			//Make sure each internal array has right length
			assertEquals(emptyMapArray[i].length, MapGenerator.WORLD_SIZE);
			for (int j = 0; j < MapGenerator.WORLD_SIZE; j++) {
				//Make sure each internal value is 0
				assertEquals(emptyMapArray[i][j], 0);
			}
		}
		generator.populateWorldMap();
		//Should have at least 10 non zero values
		int nonZeroValueCount = 0;
		int[][] populatedMapArray = generator.getWorldMap();
		for (int i = 0; i < MapGenerator.WORLD_SIZE; i++) {
			for (int j = 0; j < MapGenerator.WORLD_SIZE; j++) {
				//Make sure  there are at least 10 nonzero values
				if (populatedMapArray[i][j] > 0)
					nonZeroValueCount++;
			}
		}
		assertTrue(nonZeroValueCount >= 10);
		//Now set the world map
		generator.setWorldMap(populatedMapArray);
		//Now populate the connectors
		generator.populateConnectors();
		//Now there should be strictly more than 10 screens
		nonZeroValueCount = 0;
		populatedMapArray = generator.getWorldMap();
		for (int i = 0; i < MapGenerator.WORLD_SIZE; i++) {
			for (int j = 0; j < MapGenerator.WORLD_SIZE; j++) {
				//Make sure  there are at least 10 nonzero values
				if (populatedMapArray[i][j] > 0)
					nonZeroValueCount++;
			}
		}
		assertTrue(nonZeroValueCount > 10);
		//Just for grins, print it out
		generator.printWorldMap();
		//Fun stuff now, import tuples!
		Tuple startingScreen = generator.getStartingScreen();
		Tuple endingScreen = generator.findFurthestPoint(startingScreen, populatedMapArray);
		//startingScreen shouldn't equal endingScreen
		assertFalse(startingScreen.equals(endingScreen));
		//set boss and start screen
		generator.setBossAndStartScreen();
		//now there should be an 8 and a 7 on the map
		boolean counted8 = false;
		boolean counted7 = false;
		populatedMapArray = generator.getWorldMap();
		for (int i = 0; i < MapGenerator.WORLD_SIZE; i++) {
			for (int j = 0; j < MapGenerator.WORLD_SIZE; j++) {
				//7 and 8 or 7 and 7
				if (populatedMapArray[i][j] >= 7)
					counted8 = true;
				if (populatedMapArray[i][j] == 7)
					counted7 = true;
			}
		}
		assertTrue(counted8);
		assertTrue(counted7);
		
		generator.placeCaveScreen();
		//Nothing to assert for this one
		//This should give 100% code coverage if we run
		//it enough times because of randomness, but 99% on average is enough.
	}
	
	/**
	 * Tile tests
	 * Most of Tile was already called by other methods, just a few getters/setters
	 * are left to check.
	 */
	@Test
	public void TileTests() {
		Tile tile = new Tile("String image path is stored as string so not needed", false, 4, 1, 5, 2);
		assertEquals(tile.imgPath(), "String image path is stored as string so not needed");
		assertFalse(tile.isPassable());
		assertEquals(tile.startCol(), 4);
		assertEquals(tile.colRange(), 1);
		assertEquals(tile.startRow(), 5);
		assertEquals(tile.rowRange(), 2);
	}
}
