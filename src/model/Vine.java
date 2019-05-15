package model;

import enums.ID;
import javafx.scene.image.Image;
import view.map.WorldView;

public class Vine extends CollectableObject{
	
	private static Image objectImage = new Image("file:src/images/vine.png");

	public Vine(int x, int y, ID id, WorldView view) {
		super(objectImage, x, y, id, view);
	}

	@Override
	public void pickUp(PlayerObject player) {
		player.getInventory().addVines(1);
	}
	

}