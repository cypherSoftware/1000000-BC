package testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import model.MapGenerator;
import model.Tuple;

class MapTests {
	
	@Test
	void MapGeneratorTest() {
		MapGenerator map = new MapGenerator();
		int[][] worldMap = new int[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				worldMap[i][j] = 0;
			}
		}
		worldMap[0][0] = 1;
		worldMap[5][5] = 1;
		
		Tuple start = new Tuple(0, 0);
		Tuple end = new Tuple(5, 5);
		
		map.setWorldMap(worldMap);
		map.generatePath(start, end);
		map.printWorldMap();
	}
	
	@Test
	void FullMapGeneratorTest() {
		MapGenerator map = new MapGenerator();
		map.populateWorldMap();
		map.printWorldMap();
		map.populateConnectors();
		System.out.println();
		map.setBossAndStartScreen();
		map.printWorldMap();
	}
	
	@Test
	void CorrectNumberOfRoomsMapGeneratorTest() {
		for (int i = 0; i < 100; i++) {
			MapGenerator map = new MapGenerator();
			map.populateWorldMap();
			map.populateConnectors();
			int[][] worldMap = map.getWorldMap();
			int count = 0;
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 6; k++) {
					if (worldMap[j][k] == 1) {
						count++;
					}
				}
			}
			assertTrue(count == 10);
		}
	}
	
	@Test
	void TupleTest() {
		HashSet<Tuple> tuples = new HashSet<>();
		Tuple a = new Tuple(0, 0);
		Tuple b = new Tuple(0, 0);
		Tuple c = new Tuple(0, 1);
		
		tuples.add(a);
		assertTrue(tuples.contains(a));
		assertTrue(tuples.contains(b));
		assertFalse(tuples.contains(c));
	}

}