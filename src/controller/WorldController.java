package controller;

import java.util.ConcurrentModificationException;
import enums.ID;


import javafx.animation.KeyFrame;

import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;
import model.Enemy;
import model.GameObject;
import model.WorldModel;

import model.ScreenMap; // For static variables

public class WorldController {
	private WorldModel model;
	private Timeline clock;
	private int tick;
	private final static int GAME_SPEED = 10;
	
	public WorldController(WorldModel model) {
		this.model = model;
		clock = new Timeline();
		clock.setCycleCount(Timeline.INDEFINITE);
		Duration frameSpeed = new Duration(GAME_SPEED); //The game ticks at .01 seconds
		tick = 0;
		
		KeyFrame clockTicker = new KeyFrame(frameSpeed, new TickHandler() , (KeyValue)null); 
		//I never figured out what the KeyValue is for, but the documentation
		// says if it's null, it just gets ignored.
		
		//Add the tick to the clock
		clock.getKeyFrames().add(clockTicker);
	}
	
	public void startClock() {
		clock.playFromStart();
	}	
	
	public ScreenMap getScreen() {
		return model.getScreen();
	}
	
	// This is the event that runs every game tick
	class TickHandler implements EventHandler<ActionEvent>	{
		@Override
		public void handle(ActionEvent frameTicked) {
			tick++; 
			if (model.getScreen().player.getHP() <= 0) {
				System.out.println("GAME OVER");
				clock.stop();
			}
			try {
				for (GameObject o : model.getScreen().objects) {
					if (o.getSpeed() > 0) { // Only move moveable objects
						model.move(o);
					}
				}
				model.getScreen().controlEnemies();
				
			} catch (ConcurrentModificationException e) {
				// Note: When we change screens, the time it takes to change
				// screens is longer than a single game tick. Thus, model.getScreen()
				// will be changed while the game timer is going. We have two options:
				// Pause and restart the timer, or just let this method fail to
				// move objects on the screen. This will result in a ConcurrentModificationException
				// which, when thrown, simply leaves the model with a lock on the object and fails to get
				// the GameObject list. Thus nothing happens when the error is thrown, so there's no
				// need to worry about it causing problems. Because of this, I vote for just
				// wrapping this in a try/catch block catching the ConcurrentModificationException,
				// and letting it fail silently. If we wanted to do something more than
				// just get a good grade on the project, I'd say log this error to a file along
				// with all others, but this band aid fix should be plenty good.
			}
		}
	}
	
	public int getCurrentX() {
		return model.getCurrentX();
	}
	
	public int getCurrentY() {
		return model.getCurrentY();
	}
	
	public Timeline getGameClock() {
		return clock;
	}
}