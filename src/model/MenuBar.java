package model;

import java.util.ArrayList;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.map.WorldView;

public class MenuBar extends ImageView{
	public  static int   MENUBAR_HEIGHT  = 35;
	private static Image backgroundImage = new Image("file:src/images/menuBar.png");
	private ImageView inventoryButton;
	private ImageView minimapButton;
	private ImageView loadButton;
	private ImageView saveButton;
	private ImageView heartSymbol;
	private Rectangle healthIndicator;
	
	private PlayerObject playerPointer; // be able to reference the player's health
	private WorldView    viewPointer;   // be able to call the inventory/save/load methods from view
	//Variables here for the clickable things (nodes probably?)
	//Or possibly set a click handler that looks for current mouse placement.
	//Also need to figure out how to update the label.
	
	public MenuBar(PlayerObject player, WorldView view) {
		super(backgroundImage);
		playerPointer = player;
		viewPointer = view;
		this.setViewport(null);
		this.setFitHeight(MENUBAR_HEIGHT);
		this.setFitWidth(WorldView.SCENE_SIZE - 20);
		
		//Screen position
		this.setX(10);
		this.setY(WorldView.SCENE_SIZE - 5);
		
		//Set up buttons
		this.setUpInventoryButton();
		this.setUpSaveAndLoadButtons();
		this.setUpMinimapButton();
		
		//Set up label(s)
		this.setUpCurrentHealthIndicator();
		
	}
	
	private void setUpInventoryButton() {
		Image inventoryButtonImage = new Image("file:src/images/inventoryButton.png");
		inventoryButton = new ImageView(inventoryButtonImage);
		inventoryButton.setOnMouseClicked(new ActivateInventory());
		
		inventoryButton.setX(this.getX() + 120);
		inventoryButton.setY(this.getY() + 6);
		inventoryButton.setFitHeight(25);
		inventoryButton.setFitWidth(65);
		
	}
	
	class ActivateInventory implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			//Pause the game
			viewPointer.getGameClock().pause();
			viewPointer.openInventory();
		}
	}
	
	private void setUpSaveAndLoadButtons() {
		Image saveButtonImage = new Image("file:src/images/saveButton.png");
		saveButton = new ImageView(saveButtonImage);
		saveButton.setOnMouseClicked(new SaveGameEvent());
		
		saveButton.setX(this.getX() + 40);
		saveButton.setY(this.getY() + 4);
		saveButton.setFitHeight(15);
		saveButton.setFitWidth(30);
		
		
		
		Image loadButtonImage = new Image("file:src/images/loadButton.png");
		loadButton = new ImageView(loadButtonImage);
		loadButton.setOnMouseClicked(new LoadGameEvent());
		
		loadButton.setX(this.getX() + 40);
		loadButton.setY(this.getY() + 18);
		loadButton.setFitHeight(15);
		loadButton.setFitWidth(30);
		
	}
	
	class SaveGameEvent implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			viewPointer.saveGame();
		}
	}
	
	class LoadGameEvent implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			viewPointer.loadGame();
		}
	}
	
	private void setUpMinimapButton() {
		Image minimapButtonImage = new Image("file:src/images/MinimapButton.png");
		minimapButton = new ImageView(minimapButtonImage);
		minimapButton.setOnMouseClicked(new ActivateMinimap());
		
		minimapButton.setX(this.getX() + 405);
		minimapButton.setY(this.getY() + 6);
		minimapButton.setFitHeight(25);
		minimapButton.setFitWidth(65);
		
	}
	
	class ActivateMinimap implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			//Pause the game
			viewPointer.getGameClock().pause();
			viewPointer.openMinimap();
		}
	}
	
	private void setUpCurrentHealthIndicator() {
		Image heartSymbolImage = new Image("file:src/images/heartSymbol.png");
		heartSymbol = new ImageView(heartSymbolImage);
		
		heartSymbol.setX(this.getX() + 225);
		heartSymbol.setY(this.getY() + 2);
		
		heartSymbol.setFitHeight(30);
		heartSymbol.setFitWidth(30);
		
		healthIndicator = new Rectangle(170, 20, Color.RED);
		
		//Credit to Mr. D-Link at https://gamedev.stackexchange.com/a/168640/117863 for 
		//this way to get rectangular binding to work
		
		healthIndicator.widthProperty().multiply(playerPointer.getPercentHP());
		healthIndicator.widthProperty().bind(playerPointer.getPercentHP());
		
		healthIndicator.setX(heartSymbol.getX() + 35);
		healthIndicator.setY(heartSymbol.getY() + 5);
	}
	
	public ArrayList<Node> getMenuItems() {
		ArrayList<Node> objects = new ArrayList<Node>();
		//Use an array list so that the menu can be the first thing displayed guaranteed
		objects.add(this);
		objects.add(heartSymbol);
		objects.add(healthIndicator);
		objects.add(saveButton);
		objects.add(loadButton);
		objects.add(inventoryButton);
		objects.add(minimapButton);
		return objects;
	}
	
	
}
