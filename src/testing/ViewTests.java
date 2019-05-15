package testing;

import org.junit.Test;

import model.MapGenerator;
import view.map.WorldView;

public class ViewTests {

	
	private void printScreen(int[][] m) {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}
}
