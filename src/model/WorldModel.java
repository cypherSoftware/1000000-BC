package model;

import java.util.Observable;
import java.util.Random;

import controller.SoundEffect;
import enums.ID;
import view.map.WorldView;

/**
 * 
 * @author Mark Bzomowski (mbzomowski)
 * 
 * The game model. WorldModel extends Observable, where WorldView is the
 * observer.
 *
 */
public class WorldModel extends Observable{
	private int worldWidth;
	private int worldHeight;

	private ScreenMap[][] world;
	private int currentX;
	private int currentY;

	private WorldView observed;

	private static SoundEffect playerPain = new SoundEffect("src/effects/dsplpain.wav");
	private static SoundEffect attackHit = new SoundEffect("src/effects/dsskepch.wav");
	private static SoundEffect enemyDie = new SoundEffect("src/effects/dsslop.wav");
	private static SoundEffect itemPickup = new SoundEffect("src/effects/dsitemup.wav");
	
	private boolean beenHit = false;
	private int hitDelay = 50;


	public WorldModel(ScreenMap[][] m) {
		this.world = m;
		this.worldWidth = m[0].length;
		this.worldHeight = m.length;
	}

	//TODO: add error handling for potential bad values
	public void setScreen(int x, int y) {
		this.currentX = x;
		this.currentY = y;
	}

	public void addObserver(WorldView obs) {
		this.observed = obs;
	}

	public ScreenMap getScreen() {
		return world[currentX][currentY];
	}

	// The method that actually updates the object's location. Called by controller
	public void move(GameObject o) {
		
		
		o.changePos(o.dX(), o.dY());
		//observed.update(this, o);


		if(o.getID() == ID.Player)	{//checks if o is the player somehow
			if (o.getX() < 0  || o.getY() < 0 || 
					o.getX() > (11 * ScreenMap.RENDER_WIDTH) ||
					o.getY() > (11 * ScreenMap.RENDER_HEIGHT) ) {
				//Then we've walked off the screen.
				//Note: If this throws an out of bounds error on the world[][] map, 
				//then that's because the edge of the screen did not prevent
				//us from reaching the edge of the screen. This might be an error in
				//the procedural generation (There weren't impassable objects blocking
				//the entire edge of the screen) or the collision detection (We
				//somehow walked through the list of objects). If both of those are
				//working, then this won't throw errors.
				if (o.getX() < 0) {
					//Then move to the next screen to the left
					//And set our character's current position as the same y value,
					//but on the opposite x value
					currentX--;
					o.setX(10.5 * ScreenMap.RENDER_WIDTH);
				}
				if (o.getY() < 0) {
					//Then move to the next screen upwards
					//And set our character's current position as the same x value,
					//but on the opposite y value
					currentY--;
					o.setY(10.5 * ScreenMap.RENDER_HEIGHT);
				}
				if (o.getX() > 11 * ScreenMap.RENDER_WIDTH) {
					//Then move to the next screen to the right
					//And set our character's current position as the same y value,
					//but on the opposite x value
					currentX++;
					o.setX(0.5 * ScreenMap.RENDER_WIDTH);
				}
				if (o.getY() > 11 * ScreenMap.RENDER_HEIGHT) {
					//Then move to the next screen down
					//And set our character's current position as the same x value,
					//but on the opposite Y value
					currentY++;
					o.setY(0.5 * ScreenMap.RENDER_WIDTH);
				}

				//Debug: If this line throws an error, then we went off the edge of the world.

				world[currentX][currentY].toString();

				//Let the view know that the screen has changed
				observed.update(this, o);
			}

			//Collision Detection for tiles.  
			//This should stop the player from walking on impassable tiles

			//top corner hitbox coords
			int x = (int)(o.getX()+4)/ScreenMap.RENDER_WIDTH;
			int y = (int)(o.getY()+2)/ScreenMap.RENDER_HEIGHT;


			//bottom corner hitbox coords
			int x2 = (int)(o.getX()+24)/ScreenMap.RENDER_WIDTH;
			int y2 = (int)(o.getY()+24)/ScreenMap.RENDER_HEIGHT;


			if(!world[currentX][currentY].tileAt(x, y).isPassable() ||
					!world[currentX][currentY].tileAt(x2, y).isPassable() || 
					!world[currentX][currentY].tileAt(x, y2).isPassable() ||
					!world[currentX][currentY].tileAt(x2, y2).isPassable()) {
				//this kinda works want to try to move it above o.changePos call at the start of this method.  also want to try to set dx and dy to zero instead
				o.setX(o.X() + o.dX() * -1);
				o.setY(o.Y() + o.dY() * -1);

				//*******************new code start********************
				//snaps player back to passable tile if tile detection fails.  For example when you hold shift it moves the character into the tile before tile collision is checked

				if(!world[currentX][currentY].tileAt(x, y).isPassable() &&
						!world[currentX][currentY].tileAt(x2, y).isPassable() && 
						!world[currentX][currentY].tileAt(x, y2).isPassable() &&
						!world[currentX][currentY].tileAt(x2, y2).isPassable()) {
					if (o.getDirectionFacing() == 'U') {
						o.changePos(o.dX(), (o.dY() - 48));
					}
					else if(o.getDirectionFacing() == 'D') {
						o.changePos(o.dX(), (o.dY() + 48));
					}
					else if (o.getDirectionFacing() == 'L') {
						o.changePos((o.dX() + 48), o.dY());
					}
					else if (o.getDirectionFacing() == 'R'){
						o.changePos((o.dX() - 48), o.dY());
					}


				}
				o.setX(o.X() + o.dX() * -1);
				o.setY(o.Y() + o.dY() * -1);



				//*******************new code end**********************
			}




			//collision detection for attacking

			for(GameObject temp :  world[currentX][currentY].objects) {

				if( o.getAttackBox().intersects(temp.getBoundsInLocal())){
					if (temp.getID() != ID.Player && temp.getID() != ID.Collectable) {
						attackHit.play();
						temp.setHP(temp.getHP() - 5);
						if (temp.getHP() <= 0) {
							temp.destroy();
							Random rng = new Random();
							if (rng.nextInt(2) == 0) {
								spawnPickup(temp);
							}
							enemyDie.play();
						}
					}
				}
			}


			//Collision detection for enemies
			for(GameObject temp : world[currentX][currentY].objects) {

				if (temp.getID() == ID.Enemy) {
					if (o.getHitBox().intersects(temp.getHitBox().getBoundsInLocal())){
						
						// mburns: I commented these out because enemy collisions feel smoother without them
						//o.setX(o.X() + o.dX() * -1);
						//o.setY(o.Y() + o.dY() * -1);

						// Add a period of invlunerability after each hit
						if (beenHit) {
							if (hitDelay == 0) {
								beenHit = false;
							} else {
								hitDelay--;
							}
						} else {
							//hp-= 10 or something
							playerPain.play();
						        if(!o.getIsInvincible()) {
						   	        o.setHP(o.getHP() - 1);
						        }
							beenHit = true;
							hitDelay = 50;
						}


					}

					

				} else if (temp.getID() == ID.Collectable) {
					if (o.getHitBox().intersects(temp.getHitBox().getBoundsInLocal())){
						((CollectableObject)temp).pickUp(((PlayerObject)o));
						temp.destroy();
						itemPickup.play();
					}
				}
			}
		}
		
		
		//collision detection for spear throw
		
		if (o.getID() == ID.Spear) {
			
			for (GameObject temp: world[currentX][currentY].objects) {
				if (temp.getID() != ID.Player) {
					if(o.getHitBox().intersects(temp.getHitBox().getBoundsInLocal())){
						attackHit.play();
						temp.setHP(temp.getHP() - 10);
						if (temp.getHP() <= 0) {
							temp.destroy();
						}
					}
				}
			}
		}
		
		//trigger for grapple on river
		
		/*if(o.getID() == ID.River) {
			
			for (GameObject temp : world[currentX][currentY].objects ) {
				if(temp.getID() == ID.River)
				if(o.getHitBox().intersects(temp.getHitBox().getBoundsInLocal())) {
					cut.showCutScene;
				}
			}
		}*/
		
	}
		
	

	private void spawnPickup(GameObject temp) {
		if (temp instanceof Stalker) {
			Tooth t = new Tooth(
					((int)temp.getX() / this.getScreen().RENDER_WIDTH) + 1, 
					((int)temp.getY() / this.getScreen().RENDER_HEIGHT) + 1, 
					ID.Collectable, 
					temp.viewPointer); 
			t.setFitHeight(48);
			t.setFitWidth(48);
			temp.viewPointer.addObjectToScreen(t);
		} else if (temp instanceof Charger || temp instanceof Flier) {
			Meat m = new Meat(
					((int)temp.getX() / this.getScreen().RENDER_WIDTH) + 1, 
					((int)temp.getY() / this.getScreen().RENDER_HEIGHT) + 1, 
					ID.Collectable, 
					temp.viewPointer); 
			m.setFitHeight(48);
			m.setFitWidth(48);
			temp.viewPointer.addObjectToScreen(m);
		}
		
	}
	
	public int getCurrentX() {
		return this.currentX;
	}
	
	public int getCurrentY() {
		return this.currentY;
	}

}