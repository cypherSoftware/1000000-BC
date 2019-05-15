package model;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import view.map.WorldView;
public class TitleScreen extends ImageView {
	
	private static Image backgroundImage = new Image("file:src/images/ughhBackground.png");
	private static Image inactiveBuild   = new Image("file:src/images/hammerGrey.png");
	private static Image activeBuild     = new Image("file:src/images/hammer.png");
	private ImageView exitButton;
	private ImageView buildSpearButton;
	private ImageView buildGrappleButton;
	private int rocks;
	
	Label alertLabel               = new Label();
	Rectangle up = new Rectangle();
	StringProperty alertProperty   = new SimpleStringProperty();
	
	private ArrayList<Label> itemCounts;
	
	private WorldView viewPointer;
	
	public TitleScreen(WorldView view, boolean invincible) {
		super(backgroundImage);
		
		viewPointer = view;
		
		
		this.setX(0);
		this.setY(0);
		this.setFitWidth(WorldView.SCENE_SIZE);
		this.setFitHeight(WorldView.SCENE_SIZE + MenuBar.MENUBAR_HEIGHT);
		makeStartButton();
		makeBuildingButtons();
		
		
	}
	
	public int getRocks() {
		return rocks;
	}
	
	private void makeStartButton() {	
		Image exitButtonImage = new Image("file:src/images/x.png");
		exitButton = new ImageView(exitButtonImage);
		exitButton.setOnMouseClicked(new CloseInventory());
		up.setOnMouseClicked(e-> CheatBox.displayCheatBox());
		exitButton.setX(this.getFitWidth() - 30);
		exitButton.setY(30);
		exitButton.setFitHeight(25);
		exitButton.setFitWidth(25);
	}
	
	class CloseInventory implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			
			alertProperty.set("");
			viewPointer.remove(getObjects());
		}
	}
	
	private void makeBuildingButtons() {
		
		alertLabel.textProperty().bind(alertProperty);
		alertLabel.setLayoutX(155);
		alertLabel.setLayoutY(6);
		alertLabel.setFont(new Font(16));
		alertLabel.setTextFill(Color.RED);
		
		
		up.setX(360);
		up.setY(500);
		up.setWidth(38);
		up.setHeight(42);
		up.setArcWidth(20);
		up.setArcHeight(20);
		up.setFill(new Color(1.0,1.0,1.0,0.0));
		//up.setFill(Color.ANTIQUEWHITE);
		
	}
	
	
	class ActivateCheatBox implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			viewPointer.openInventory();
		}
	}
	
	
	
	/*class BuildSpear implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			alertProperty.set("");
			if (rocks > 0 && sticks > 0) {
				//Then create the spear
				removeRocks(1);
				removeSticks(1);
				addSpears(1);
			} else {
				//Then block spear creation
				alertProperty.set("You need a rock and a stick to make a spear.");
			} 
		}
	}
	
	class BuildGrapple implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			alertProperty.set("");
			if (spears > 0 && vines > 0) {
				//Then create the spear
				removeSpears(1);
				removeVines(1);
				addGrapples(1);
			} else {
				//Then block spear creation
				alertProperty.set("You need a spear and a vine to make a grapple.");
			} 
		}
	}
	
	private void updateSpearButton() {
		if (rocks > 0 && sticks > 0) {
			//Then allow spear creation if we aren't already allowing it
			if (!buildSpearButton.getImage().equals(activeBuild)) {
				buildSpearButton.setImage(activeBuild);
			}
		} else {
			//Then block spear creation if we aren't already blocking it
			if (!buildSpearButton.getImage().equals(inactiveBuild)) {
				buildSpearButton.setImage(inactiveBuild);
			}
		}
	}
	
	private void updateGrappleButton() {
		if (spears > 0 && vines > 0) {
			//Then allow spear creation if we aren't already allowing it
			if (!buildGrappleButton.getImage().equals(activeBuild)) {
				buildGrappleButton.setImage(activeBuild);
			}
		} else {
			//Then block spear creation if we aren't already blocking it
			if (!buildGrappleButton.getImage().equals(inactiveBuild)) {
				buildGrappleButton.setImage(inactiveBuild);
			}
		}
	}
	
	private void makeDisplayNumbers() {
		itemCounts = new ArrayList<Label>();
		Label rockLabel = new Label();
		Label stickLabel = new Label();
		Label spearLabel = new Label();
		Label vineLabel = new Label();
		Label grappleLabel = new Label();
		
		
		rockProperty.set("" + rocks);
		rockLabel.textProperty().bind(rockProperty);
		rockLabel.setLayoutX(390);
		rockLabel.setLayoutY(65);
		rockLabel.setFont(new Font(30));
		rockLabel.setTextFill(Color.CRIMSON);
		
		stickProperty.set("" + sticks);
		stickLabel.textProperty().bind(stickProperty);
		stickLabel.setLayoutX(390);
		stickLabel.setLayoutY(125);
		stickLabel.setFont(new Font(30));
		stickLabel.setTextFill(Color.CRIMSON);
		
		spearProperty.set("" + spears);
		spearLabel.textProperty().bind(spearProperty);
		spearLabel.setLayoutX(390);
		spearLabel.setLayoutY(185);
		spearLabel.setFont(new Font(30));
		spearLabel.setTextFill(Color.CRIMSON);
		
		vineProperty.set("" + vines);
		vineLabel.textProperty().bind(vineProperty);
		vineLabel.setLayoutX(390);
		vineLabel.setLayoutY(255);
		vineLabel.setFont(new Font(30));
		vineLabel.setTextFill(Color.CRIMSON);
		
		grappleProperty.set("" + grapples);
		grappleLabel.textProperty().bind(grappleProperty);
		grappleLabel.setLayoutX(390);
		grappleLabel.setLayoutY(335);
		grappleLabel.setFont(new Font(30));
		grappleLabel.setTextFill(Color.CRIMSON);*/
		
		
	/*	itemCounts.add(rockLabel);
		itemCounts.add(stickLabel);
		itemCounts.add(spearLabel);
		itemCounts.add(vineLabel);
		itemCounts.add(grappleLabel);
		
		
	}*/
	
	public ArrayList<Node> getObjects() {
		ArrayList<Node> localObjects = new ArrayList<Node>();
		//Putting this first guarantees that the Inventory is drawn before buttons
		localObjects.add(this);
		localObjects.add(exitButton);
		localObjects.add(up);
	
		return localObjects;
	}
}