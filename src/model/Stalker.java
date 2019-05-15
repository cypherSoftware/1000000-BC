package model;

import java.util.Random;

import controller.SoundEffect;
import enums.ID;
import view.map.WorldView;
import javafx.scene.image.Image;
import view.map.WorldView;

public class Stalker extends Enemy {

	private int wait;
	private boolean attacking;
	static SoundEffect growl = new SoundEffect("src/effects/dssgtatk.wav");

	public Stalker(Image i, int x, int y, ID id, WorldView view) {
		super(i, x, y, id, view);
		wait = 50;
		attacking = true;
		this.setHP(125);
	}

	@Override
	public void checkScreenBounds(ScreenMap screen) {
		if ((int) (this.getX() / screen.RENDER_WIDTH) == 10) {
			this.setXMove(-1);
			this.spriteSheet.setNewAnimation(12, 5);
		} else if ((int) (this.getX() / screen.RENDER_WIDTH) == 0) {
			this.setXMove(1);
			this.spriteSheet.setNewAnimation(12, 2);
		} else if ((int) (this.getX() / screen.RENDER_HEIGHT) == 10) {
			this.setYMove(-1);
		} else if ((int) (this.getY() / screen.RENDER_HEIGHT) == 0) {
			this.setYMove(1);
		}
	}

	@Override
	public void attack(ScreenMap screen) {
		this.setViewport(spriteSheet.nextSprite());
		if (wait == 0) {
			if (attacking) {
				attacking = false;
				wait = 100;
			} else {
				growl.play();
				attacking = true;
				wait = 50;
			}
		} else {
			wait--;
		}

		if (attacking) {
			//this.setViewport(spriteSheet.nextSprite());
			if (screen.player.getX() > this.getX()) {
				this.setXMove(1);
				this.spriteSheet.setNewAnimation(12, 5);
			} else {
				this.setXMove(-1);
				this.spriteSheet.setNewAnimation(12, 2);
			}

			if (screen.player.getY() > this.getY()) {
				this.setYMove(1);
			} else {
				this.setYMove(-1);
			}
		} else {
			this.setXMove(0);
			this.setYMove(0);
		}
	}
	
	@Override
	public void destroy() {
		Random rng = new Random();
		if (rng.nextInt(4) == 0) {
			
		}
		super.destroy();
	}

}
