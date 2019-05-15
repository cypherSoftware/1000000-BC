package model;

import enums.ID;
import javafx.scene.image.Image;
import view.map.WorldView;

public class Meat extends CollectableObject{
	
	private static Image objectImage = new Image("file:src/images/bone.png");

	public Meat(int x, int y, ID id, WorldView view) {
		super(objectImage, x, y, id, view);
	}

	@Override
	public void pickUp(PlayerObject player) {
		//Recover 20 health
		player.setHP(player.getHP() + 25);
	}
	

}