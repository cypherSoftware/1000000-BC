package model;

import enums.ID;
import javafx.scene.image.Image;
import view.map.WorldView;

public class SuperMeat extends CollectableObject{
	
	private static Image objectImage = new Image("file:src/images/bone.png");

	public SuperMeat(int x, int y, ID id, WorldView view) {
		super(objectImage, x, y, id, view);
	}

	@Override
	public void pickUp(PlayerObject player) {
		//Recover all health
		player.setHP(player.getMaxHP());
	}
	

}