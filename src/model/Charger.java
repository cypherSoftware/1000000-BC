package model;

import controller.SoundEffect;
import enums.ID;
import javafx.geometry.Rectangle2D;
import view.map.WorldView;
import javafx.scene.image.Image;

public class Charger extends Enemy {

	private boolean facingRight;
	static SoundEffect roar = new SoundEffect("src/effects/dscybsit.wav");	
	
	public Charger(Image i, int x, int y, ID id, WorldView view) {
		super(i, x, y, id, view);
		this.setHP(200);
		this.facingRight = true;
	}

	@Override
	public void checkScreenBounds(ScreenMap screen) {
		// Stop at impassable tiles and face other direction
		if (facingRight && (int) (this.getX() / screen.RENDER_WIDTH) == 10) {
			this.setXMove(0);
			this.spriteSheet.setNewAnimation(7, 0);
			setFacingRight(false);
		} else if (!facingRight && (int) (this.getX() / screen.RENDER_WIDTH) == 0) {
			this.setXMove(0);
			this.spriteSheet.setNewAnimation(7, 1);
			setFacingRight(true);
		}
	}

	@Override
	public void attack(ScreenMap screen) {
		checkScreenBounds(screen);
		if (screen.player.getY() < this.getY() + 20 && screen.player.getY() > this.getY()) {
			if (this.facingRight && screen.player.getX() > this.getX()) {
				this.setXMove(1);
				this.setViewport(spriteSheet.nextSprite());
			} else if (!this.facingRight && screen.player.getX() < this.getX()) {
				this.setXMove(-1);
				this.setViewport(spriteSheet.nextSprite());
			}
			roar.play();
		}
	}

	public void setFacingRight(boolean isFacingRight) {
		this.facingRight = isFacingRight;
	}

}
