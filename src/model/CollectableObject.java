package model;

import enums.ID;
import javafx.scene.image.Image;
import view.map.WorldView;

public abstract class CollectableObject extends GameObject {
	
	public CollectableObject(Image i, int x, int y, ID id, WorldView view) {
		super(i, x, y, id, view);
	}


	public abstract void pickUp(PlayerObject player); 
	
	
	
}
