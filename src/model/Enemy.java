package model;

import enums.ID;
import view.map.WorldView;
import javafx.scene.image.Image;
import enums.ID;
import javafx.scene.image.Image;
import view.map.WorldView;

public abstract class Enemy extends GameObject{
	
	public Enemy(Image i, int x, int y, ID id, WorldView view) {
		super(i, x, y, id, view);
	}
	
	public abstract void checkScreenBounds(ScreenMap screen);
	public abstract void attack(ScreenMap s);

}
