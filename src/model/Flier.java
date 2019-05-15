package model;

import java.util.Random;

import controller.SoundEffect;
import enums.ID;
import view.map.WorldView;
import javafx.scene.image.Image;

public class Flier extends Enemy {
	
	private int countdown;
	private boolean facingRight;
	static SoundEffect screech = new SoundEffect("src/effects/dssklatk.wav");

	public Flier(Image i, int x, int y, ID id, WorldView view) {
		super(i, x, y, id, view);
		countdown = 30;
		this.facingRight = true;
	}
	@Override
	public void checkScreenBounds(ScreenMap screen) {
		int x = (int)(this.getX() / screen.RENDER_WIDTH);
		int y = (int)(this.getY() / screen.RENDER_HEIGHT);
		if (x >= 10) {
			this.setXMove(-1);
			this.spriteSheet.setNewAnimation(4, 1);
		} else if (x <= 0) {
			this.setXMove(1);
			this.spriteSheet.setNewAnimation(4, 0);
		} else if (y >= 10) {
			this.setYMove(-1);
		} else if (y <= 0) {
			this.setYMove(1);
		}
	}
	
	@Override
	public void attack(ScreenMap screen) {
		if (this.countdown > 0) {
			countdown--;
		} else {
			Random rng = new Random();
			int dx = rng.nextInt(3);
			int dy = rng.nextInt(3);
			if (dx == 2) {
				dx = -1;
			}
			if (dy == 2) {
				dy = -1;
			}
			this.setXMove(dx);
			this.setYMove(dy);
			this.setSpeed(rng.nextInt(3) + 1);
			this.countdown = rng.nextInt(70) + 30;
			//Make this play much less frequently, because every time makes it lag horribly
			if (rng.nextInt(80) == 7) 
				screech.play();
		}
		this.setViewport(spriteSheet.nextSprite());
		checkScreenBounds(screen);
		
	}

}
