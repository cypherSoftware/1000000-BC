package view.map;

/**
 * This class is just a test of trying to get a character to move around the stage in a way
 * that will allow multiple things to move on the screen while we're giving instructions to
 * the character.
 * 
 * Overview: This class simulates the MobileObject class (see characters package) with a Movable
 * class. It extends ImageView, and adds some control variables so that a character can be one
 * of several Movable objects on the screen. A Timeline object runs constantly, and every .01 seconds,
 * it checks if anything needs to be moved, and if so, it moves it. ImageViews are convenient because
 * they allow changing the image that they're casting, and this whole setup repaints the
 * window so that we don't get a line of the sprite trailing it.
 * 
 * I've tried to supply many comments so that any of this code can be reused. Ideally, the map will
 * contain scene objects that incorporate most of this logic, and the event handlers will belong
 * to each individual mobile object (the children of the stage can contain each object that needs
 * to be movable so that they can all be moved at once, on each game tick). That way, we can
 * easily divide up the work of what all we need to do.
 * 
 * 
 */


import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import controller.SpriteSheet;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;

public class TestStage extends Application {
	//Stage is the entire window. It currently ends up holding a single scene which is
	//the entire window. What we'll probably want to use is a BorderPane with the center
	//being the screen we swap out, and the bottom being a menu bar, top being a file menu,
	//and so forth.
	Stage stage;
	//This variable keeps track of how many ticks of the game have gone by. One tick is
	//0.01 seconds. We might need to think about what happens when this variable overflows; 
	//since we use mod operations on it, it might offset the game by a couple ticks if 
	//we aren't careful, or maybe it'll just work.
	int   frame;
	

	public static void main(String args[]) {
		//Launch this rocket
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//This frame will start counting as soon as the game starts. I'll have it 
		//print to the console for visibility, but obviously that can't exist in the
		//finished product.
		frame = 0;
		this.stage = stage;
		this.stage.setTitle("Test Stage");
		this.stage.setWidth(500);
		this.stage.setHeight(500);
		
		//ImageView is a class that seems to make displaying images for javafx super easy
		//Hopefully we don't run into any unexpected errors using it.
		ImageView background = new ImageView();
		//This file comes from https://opengameart.org/sites/default/files/map_2.png
		//But that probably won't matter since I doubt it'll be in the final product
		background.setImage(new Image("file:src/images/TestBackground.png"));
		background.setFitHeight(504); //No special meaning to these numbers,
		background.setFitWidth(504); //I just picked something that fit. Feel free to modify
		
		//Movable is a private class defined at the end of this test class. 
		//MobileObject.java is meant to do basically the same thing. It extends ImageView
		//so that it can do everything ImageView can, and additionally hold metadata that
		//lets the game clock know if and when to move the Movable object.
		//Right now I'm only making one Movable object, but this code should work for
		//multiple on the screen, although only one will have keypress behavior (the user's
		//character), and the rest will have predefined movement paths.
		Movable character = new Movable();
		//TestCharacter.png and it's related files are proudly made on MS Paint.
		//Y'all better come up with better sprites. Speaking of which, there's
		//a sprite class that can be used in this same Image defintion, similar to
		//character.setImage(SpriteName[++frame % intNumOfImagesInSprite]);
		//Though that was javafx2 Syntax, I'm not sure if the java8 integrated fx syntax
		//changes any. For furhter reference on applying sprites,
		//see http://silveiraneto.net/2008/12/08/javafx-how-to-create-a-rpg-like-game/
		character.setImage(new Image("file:src/images/TestCharacter.png"));
		character.setX(250); //Starting position
		character.setY(300);
		character.setFitWidth(50); //Size of sprite -- Draw it high quailty, then shrink it with
		character.setFitHeight(50); //the fitWidth and fitHeight, that way it looks good
		
		//Now we're going to set what happens to this character when keys are pressed or released
		//That's of course up, right, down, and left (And diagonals by extension)
		//And I also added in Hold Shift to Run
		character.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyPressed) {
				System.out.println("Key was pressed"); //Just printing for debugging
				if (keyPressed.getCode() == KeyCode.UP) {
					character.setMovingUp(); //All these 'sets' are metadata added for movement 
				} else if (keyPressed.getCode() == KeyCode.RIGHT) {
					character.setMovingRight();
				} else if (keyPressed.getCode() == KeyCode.DOWN) {
					character.setMovingDown();
				} else if (keyPressed.getCode() == KeyCode.LEFT) {
					character.setMovingLeft();
				} else if (keyPressed.getCode() == KeyCode.SHIFT) {
					character.setRunning();
				}
			}
		});
		
		//By separating pressed and released, we can have the character
		//keep moving as the key is held down
		character.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyReleased) {
				System.out.println("Key was released");
				if (keyReleased.getCode() == KeyCode.UP) {
					character.unsetMovingUp();
				} else if (keyReleased.getCode() == KeyCode.RIGHT) {
					character.unsetMovingRight();
				} else if (keyReleased.getCode() == KeyCode.DOWN) {
					character.unsetMovingDown();
				} else if (keyReleased.getCode() == KeyCode.LEFT) {
					character.unsetMovingLeft();
				} else if (keyReleased.getCode() == KeyCode.SHIFT) {
					character.unsetRunning();
				}
			}
		});
		
		character.setFocusTraversable(true); //keyPressed and keyReleased events won't be
											 //recorded unless this is set to true, I don't
											 //know why yet.
		
		//Now we're going to make the game clock
		//This will run during the whole game, and tells the game when to move
		//things in the scene. This might be a scene specific object or
		//be bound to the stage, I'm not sure yet, but hopefully this gives
		//us enough structure that one person can work on the map, another on
		//the beasts that move on the map, another on the obstacles, and so forth.
		//Speaking of obstacles, the guide for sprites I mentioned earlier will
		//also be useful for figuring out how to make obstacles impassable.
		//I'll link it again here for good measure:
		// http://silveiraneto.net/2008/12/08/javafx-how-to-create-a-rpg-like-game/
		Timeline gameClock = new Timeline();
		//This INDEFINITE makes it so that the gameClock doesn't stop on its own
		gameClock.setCycleCount(Timeline.INDEFINITE);
		//Duration's parameter is in Milliseconds
		Duration frameSpeed = new Duration(10); //The game ticks at .01 seconds
		//On each tick of the game clock, the handle is called.
		KeyFrame clockTicker = new KeyFrame(frameSpeed, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent frameTicked) {
				//This is printed for debugging; I fear that when frame flips to
				//negative due to an overflow of the int, that the game clock might get
				//offset when we mod by a negative number. Thus I figure leave it doing
				//this as we're figuring things out, then remove the print later.
				System.out.println(++frame);
				//for movable object in the scene, do the following
				//Since there's only one I didn't do a for loop, but we could loop over
				//all movable objects in the scene and do this for all of them. Supposing
				//that there aren't too many, there shouldn't be any lag, since it has
				//10 ms to wrap things up.
				if (character.isMovingUp()) {
					//This next if statement makes it so that the character only moves at
					//the speed given.
					if ((!character.isRunning() && frame % character.getSpeed() == 0) ||
					     (character.isRunning() && frame % character.getRunningSpeed() == 0)) {
						character.setY(character.getY() - 5); //Only move at this character's speed
					}
					
					//Here setImage can be set to a sprite; sprites can be identified by
					//what frame of their image they're on, and be preloaded so that
					//it's not a new image every time. See above for more info
					character.setImage(new Image("file:src/images/TestCharacterBack.png"));
					
					
				}
				if (character.isMovingRight()) {
					if ((!character.isRunning() && frame % character.getSpeed() == 0) ||
					    (character.isRunning() && frame % character.getRunningSpeed() == 0)) {
						character.setX(character.getX() + 5);
					}
					character.setImage(new Image("file:src/images/TestCharacterRight.png"));
				}
				if (character.isMovingDown()) {
					if ((!character.isRunning() && frame % character.getSpeed() == 0) ||
						 (character.isRunning() && frame % character.getRunningSpeed() == 0)) {
						character.setY(character.getY() + 5);
					}
					character.setImage(new Image("file:src/images/TestCharacter.png"));
				}
				if (character.isMovingLeft()) {
					if ((!character.isRunning() && frame % character.getSpeed() == 0) ||
						 (character.isRunning() && frame % character.getRunningSpeed() == 0)) {
						character.setX(character.getX() - 5);
					}
					//The order of these buttons shows that if you press left and up, the 
					//left image will show
					character.setImage(new Image("file:src/images/TestCharacterLeft.png"));
				}
			}
		}, (KeyValue)null); //I never figured out what the KeyValue is for, but the documentation
		// says if it's null, it just gets ignored. Might be useful when we make the whole game.
		
		//Add the tick to the clock
		gameClock.getKeyFrames().add(clockTicker);
		
		//Start the clock
		gameClock.playFromStart();
		
		//Everything that will be on the scene will be grouped as images
		Group images = new Group();
		images.getChildren().add(background);
		images.getChildren().add(character);
		
		this.stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() 
		{
			//This even is called when window is being closed
			@Override
			public void handle(WindowEvent windowEvent) 
			{
				//Save the game or something
			}
		});
		
		Scene mainScene = new Scene(images, 495, 495);
		this.stage.setScene(mainScene);
		this.stage.show();
		
		
	}
	
	private class Movable extends ImageView{
		//This class allows the Movable object to know which way its moving
		private boolean movingLeft   = false;
		private boolean movingRight  = false;
		private boolean movingUp     = false;
		private boolean movingDown   = false;
		private boolean running      = false;
		private int     speed        = 10; //Move once per 10 game ticks (.1 seconds)
		private int     runningSpeed = 3; //Move once per 3 game ticks (.03 seconds)
		
		public boolean isMovingUp() {
			return movingUp;
		}
		
		public boolean isMovingRight() {
			return movingRight;
		}
		
		public boolean isMovingDown() {
			return movingDown;
		}
		
		public boolean isMovingLeft() {
			return movingLeft;		
		}
		
		public boolean isRunning() {
			return running;
		}
		
		public void setMovingUp() {
			movingUp = true;
		}
		
		public void setMovingRight() {
			movingRight = true;
		}
		
		public void setMovingDown() {
			movingDown = true;
		}
		
		public void setMovingLeft() {
			movingLeft = true;
		}
		
		public void setRunning() {
			running = true;
		}
		
		public void unsetMovingUp() {
			movingUp = false;
		}
		
		public void unsetMovingRight() {
			movingRight = false;
		}
		
		public void unsetMovingDown() {
			movingDown = false;
		}
		
		public void unsetMovingLeft() {
			movingLeft = false;
		}
		
		public void unsetRunning() {
			running = false;
		}
		
		public int getSpeed() {
			return speed;
		}
		
		public int getRunningSpeed() {
			return runningSpeed;
		}
	}
	
}
