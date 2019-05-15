package model;

import enums.ID;
import javafx.scene.image.Image;
import view.map.WorldView;

public class Stick extends CollectableObject{
	
	private static Image objectImage = new Image("file:src/images/stick.png");

	public Stick(int x, int y, ID id, WorldView view) {
		super(objectImage, x, y, id, view);
	}

	@Override
	public void pickUp(PlayerObject player) {
		player.getInventory().addSticks(1);
	}
	

}