package model;

import enums.ID;
import javafx.scene.image.Image;
import view.map.WorldView;

public class Tooth extends CollectableObject{
	
	private static Image objectImage = new Image("file:src/images/tRexFang.png");

	public Tooth(int x, int y, ID id, WorldView view) {
		super(objectImage, x, y, id, view);
	}

	@Override
	public void pickUp(PlayerObject player) {
		player.getInventory().addTeeth(5);
	}
	

}
