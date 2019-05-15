package view.map;

import javafx.stage.Stage;
import model.GameObject;
import model.MenuBar;
import model.Minimap;
import model.PlayerObject;
import model.ScreenMap;
import model.Spear;
import model.Tile;
import model.WorldGenerator;
import model.WorldModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import controller.BackgroundMusic;
import controller.CutScenes;
import controller.SoundEffect;
import controller.WorldController;
import enums.ID;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * 
 * @author Mark Bzomowski (mbzomowski)
 * @author David Minch (minchdavidm)
 * 
 *
 */
public class WorldView extends Application implements Observer{
	Stage stage;
	
	private WorldModel model;
	private ScreenMap screen;	// the current screen
	private GridPane grid;		// needs to be accessible from multiple methods
	private BackgroundMusic backgroundMusic;
	private MenuBar onScreenMenu;
	private Minimap minimap;
	private int[][] worldMap;
	
	public static int SCENE_SIZE = ScreenMap.RENDER_HEIGHT * 12 + 20; //+20 for padding

	private WorldController controller;
	
	SoundEffect woosh = new SoundEffect("src/effects/dsskeswg.wav");
	
	boolean gameIsOver = false;
	
	Group EVERYTHING_ON_SCREEN = new Group();
											// file:src/images/ughhhSpearSpriteSheet.png
	PlayerObject player = new PlayerObject("file:src/images/ughhh spritesheet.png", 6, 8, ID.Player, this);
	Spear playerSpear = new Spear("file:src/images/spear.png", 13, 12, ID.Spear, this);
	CutScenes cut = new CutScenes();
	
	
	public static void main(String args[]) {
		//Launch this rocket
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {	
		
		Stage window = stage;
		window.setTitle("100,000 B.C.E");
		
		backgroundMusic = new BackgroundMusic();
		
		model = WorldGenerator.proceduralGeneration(this);
		

		controller = new WorldController(model);

		screen = controller.getScreen();
		Random rng = new Random();
		if (rng.nextInt(2) == 0) {
			backgroundMusic.setSong("src/music/GAME2.wav");
		} else {
			backgroundMusic.setSong("src/music/GAME1.wav");
		}
		//backgroundMusic.setSong(screen.getBackgroundMusic());
		screen.addPlayer(player);
		screen.addSpear(playerSpear);
		player.setSpeed(1);
		playerSpear.setSpeed(2);
		
		//Create the GridPane to store the tiles
		grid = new GridPane();
		
		// 10 pixel border padding
		grid.setPadding(new Insets(10, 10, 10, 10));
		
		
		drawTiles();		
		drawObjects();
		createPlayer();
		
		
		EVERYTHING_ON_SCREEN.getChildren().addAll(screen.objects);
		
		Scene scene = new Scene(EVERYTHING_ON_SCREEN, SCENE_SIZE, SCENE_SIZE + MenuBar.MENUBAR_HEIGHT );
		
		
		onScreenMenu = new MenuBar(player, this);
		EVERYTHING_ON_SCREEN.getChildren().addAll(onScreenMenu.getMenuItems());
		
		//comment this out to avoid the title screen
		openTitleScreen();
		

		
		minimap = new Minimap(this);
		
		
		window.setScene(scene);
		window.setResizable(false);
		window.show();
		
		controller.startClock();

	}

	public void createPlayer() {
		
		Rectangle2D viewport = new Rectangle2D(
				0,
				512,
				256,
				256				
				);
		player.setViewport(viewport);
		player.setX(player.X());
		player.setY(player.Y());
		player.setFitWidth(48);
		player.setFitHeight(48);
		
		player.setOnKeyPressed(new MoveControlPressed());
		player.setOnKeyReleased(new MoveControlReleased());
		player.setFocusTraversable(true);
		
		Rectangle2D spearViewport = new Rectangle2D(
				0,
				0,
				256,
				256
				);
		
		playerSpear.setViewport(spearViewport);
		playerSpear.setX(playerSpear.X());
		playerSpear.setY(playerSpear.Y());
		playerSpear.setFitWidth(48);
		playerSpear.setFitHeight(48);
		
		
	}
	
	public void drawObjects() {
		for (GameObject o : screen.objects) {
			if (o.equals(player)) {
				continue;
			}
			//TODO: Set up to work with viewport; we might need to give the object
			//instructions as to what view it's currently using
			if (o.getID().equals(ID.Collectable)) {
				//Then no viewport
				o.setFitWidth(48);
				o.setFitHeight(48);
				continue;
			}
			Rectangle2D viewport = new Rectangle2D(
					0,
					256,
					256,
					256				
					);
			o.setViewport(viewport);
			o.setFitWidth(48);
			o.setFitHeight(48);
			
		}
	}
	
	public void drawTiles() {
		int tileCol;
		int tileRow;
		
		Image x = new Image(screen.tileAt(0,0).imgPath());
		
		
		for (int i = 0; i < ScreenMap.SCREEN_WIDTH; i++) {
			for (int j = 0; j < ScreenMap.SCREEN_HEIGHT; j++) {
				Tile thisTile = screen.tileAt(i, j);
				ImageView img = new ImageView(x);

				// Randomly pick a tile to draw
				Random rng = new Random();
				tileCol = rng.nextInt(thisTile.colRange()) + thisTile.startCol();
				tileRow = rng.nextInt(thisTile.rowRange()) + thisTile.startRow();
				
				Rectangle2D viewport = new Rectangle2D( 
						tileCol * ScreenMap.RENDER_WIDTH,		// X on sheet
						tileRow * ScreenMap.RENDER_HEIGHT,		// Y on sheet
						ScreenMap.RENDER_WIDTH,				// width
						ScreenMap.RENDER_HEIGHT				// height
						);
				img.setViewport(viewport);
				grid.add(img, i, j);
			}
		}
		
		EVERYTHING_ON_SCREEN.getChildren().add(grid);
		
		
	}

	@Override
	public void update(Observable arg0, Object objectToUpdate) {
		//The screen has been changed; view new screen and put player on it
		screen.objects.remove(player);
		screen.objects.remove(playerSpear);
		screen = controller.getScreen();
		screen.addPlayer(player);
		screen.addSpear(playerSpear);
		//backgroundMusic.setSong(screen.getBackgroundMusic());
		EVERYTHING_ON_SCREEN.getChildren().clear();
		drawObjects();
		drawTiles();
		EVERYTHING_ON_SCREEN.getChildren().addAll(screen.objects);
		EVERYTHING_ON_SCREEN.getChildren().addAll(onScreenMenu.getMenuItems());
		
	}
	
	// This exists to draw pickups when they've been dropped by dead enemies
	public void addObjectToScreen(GameObject o) {
		screen.objects.add(o);
		EVERYTHING_ON_SCREEN.getChildren().add(o);
	}
	
	class MoveControlPressed implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent keyPressed) {
			if (keyPressed.getCode() == KeyCode.UP) {
				// If we aren't already moving up:
				
				if (player.dY() != -1) {
					player.setYMove(-1);
					player.setDirectionFacing('U');
					
					if( player.hasSpear()) {
					player.spriteSheet.setNewAnimation(4, 1);
					}else {
						player.spriteSheet.setNewAnimation(4, 0);
					}
				}
				
				player.setViewport(player.spriteSheet.nextSprite());
				
			} else if (keyPressed.getCode() == KeyCode.RIGHT) {
				if (player.dX() != 1) {
					player.setXMove(1);
					player.setDirectionFacing('R');
					
					if( player.hasSpear()) {
					player.spriteSheet.setNewAnimation(5, 2);
					}
					else {
						player.spriteSheet.setNewAnimation(5, 4);
					}
				}
					player.setViewport(player.spriteSheet.nextSprite());
					
			} else if (keyPressed.getCode() == KeyCode.DOWN) {		
				if (player.dY() != 1) {
					player.setYMove(1);
					player.setDirectionFacing('D');
					
					if( player.hasSpear()) {
					player.spriteSheet.setNewAnimation(4, 0);
					}
					else {
						player.spriteSheet.setNewAnimation(4, 2);
					}
				}
					player.setViewport(player.spriteSheet.nextSprite());
				
			} else if (keyPressed.getCode() == KeyCode.LEFT) {
				if (player.dX() != -1) {
					player.setXMove(-1);
					player.setDirectionFacing('L');
					
					if( player.hasSpear()) {
					player.spriteSheet.setNewAnimation(5, 3);
					}
					else {
						player.spriteSheet.setNewAnimation(5, 6);
					}
				}
					player.setViewport(player.spriteSheet.nextSprite());
				
			} else if (keyPressed.getCode() == KeyCode.SHIFT) {
				player.setSpeed(2);
			} 
			else if (keyPressed.getCode() == KeyCode.SPACE) {
				player.setIsAttacking(true);
				woosh.play();
				
				//spear throwing on button press
				if(player.hasSpear()) {
					
					playerSpear.setX(player.getX());
					playerSpear.setY(player.getY());
					System.out.println("(" + playerSpear.getX() + "," + playerSpear.getY() + ")");
					
					if(player.getDirectionFacing() == 'R') {
					playerSpear.setXMove(1);
					playerSpear.setYMove(0);
					}
					
					if(player.getDirectionFacing() == 'L') {
						playerSpear.setXMove(-1);
						playerSpear.setYMove(0);
						}
					
					if(player.getDirectionFacing() == 'U') {
						playerSpear.setYMove(-1);
						playerSpear.setXMove(0);
					}
					
					if(player.getDirectionFacing() == 'D'){
						playerSpear.setYMove(1);
						playerSpear.setXMove(0);
					}
				}
				
				
				
				
				
				
				//put appropriate viewport for direction of club attack
				if(player.getDirectionFacing() == 'U' && !player.hasSpear())
				player.setViewport(new Rectangle2D(
						256,
						256,
						256,
						256
						));
				
				if(player.getDirectionFacing() == 'D' && !player.hasSpear())
					player.setViewport(new Rectangle2D(
							256,
							768,
							256,
							256
							));
				
				if(player.getDirectionFacing() == 'L' && !player.hasSpear())
					player.setViewport(new Rectangle2D(
							256,
							1792,
							256,
							256
							));
				
				if(player.getDirectionFacing() == 'R' && !player.hasSpear())
					player.setViewport(new Rectangle2D(
							256,
							1280,
							256,
							256
							));
				
				
				
			}
			
			
			
			
			
			
			
		}
		
	}
	
	class MoveControlReleased implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent keyReleased) {
			if (keyReleased.getCode() == KeyCode.UP) {
				player.setYMove(0);
			} else if (keyReleased.getCode() == KeyCode.RIGHT) {
				player.setXMove(0);
			} else if (keyReleased.getCode() == KeyCode.DOWN) {
				player.setYMove(0);
			} else if (keyReleased.getCode() == KeyCode.LEFT) {
				player.setXMove(0);
			} else if (keyReleased.getCode() == KeyCode.SHIFT) {
				player.setSpeed(1);
			}
			else if (keyReleased.getCode() == KeyCode.SPACE) {
				player.setIsAttacking(false);
				
				if (player.hasSpear())
				player.setViewport(new Rectangle2D(
						0,
						512,
						256,
						256
						));
			}
			
		}
		
	}
	
	private void openTitleScreen() {
		
		EVERYTHING_ON_SCREEN.getChildren().addAll(player.getTitleScreen().getObjects());
		
	}
	
	public void openInventory() {
		EVERYTHING_ON_SCREEN.getChildren().addAll(player.getInventory().getObjects());
	}
	
	public void openMinimap() {
		minimap.drawMap();
		EVERYTHING_ON_SCREEN.getChildren().addAll(minimap.getObjects());
	}
	
	public void saveGame() {
		//TODO Implement me
		System.out.println("Game saved");
	}
	
	public void loadGame() {
		//TODO Implement me
		System.out.println("Game loaded");
	}

	/**
	 * This method allows a visible item to be removed from the view.
	 * @param objects An ArrayList of all items to be removed from the current view.
	 */
	public void remove(ArrayList<Node> objects) {
		EVERYTHING_ON_SCREEN.getChildren().removeAll(objects);
		screen.objects.removeAll(objects);
	}
	
	/**
	 * This method allows objects to be added to the screen due to an event.
	 * @param objects AnArrayList of all items to be added to the current screen.
	 */
	public void addObjects(ArrayList<GameObject> objects) {
		for (GameObject obj : objects) {
			if (!EVERYTHING_ON_SCREEN.getChildren().contains(obj)){
				EVERYTHING_ON_SCREEN.getChildren().add(obj);
				screen.objects.add(obj);
			}
		}
	}
	
	/**
	 * This method allows the minimap to view the world map to base it's calculations
	 * @return int[][] the World Map
	 */
	public int[][] getWorldMap() {
		return this.worldMap;
	}
	
	public void setWorldMap(int[][] map) {
		this.worldMap = map;
	}
	
	public WorldController getController() {
		return this.controller;
	}
	
	public Timeline getGameClock() {
		return controller.getGameClock();
	}
}
