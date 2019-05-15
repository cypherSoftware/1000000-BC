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
import javafx.scene.text.Font;
import view.map.WorldView;
public class Inventory extends ImageView {
	
	private static Image backgroundImage = new Image("file:src/images/Inventory.png");
	private static Image inactiveBuild   = new Image("file:src/images/hammerGrey.png");
	private static Image activeBuild     = new Image("file:src/images/hammer.png");
	private ImageView exitButton;
	private ImageView buildSpearButton;
	private ImageView buildGrappleButton;
	private int teeth;
	StringProperty toothProperty = new SimpleStringProperty();
	private int sticks;
	StringProperty stickProperty = new SimpleStringProperty();
	private int spears;
	StringProperty spearProperty = new SimpleStringProperty();
	private int vines;
	StringProperty vineProperty = new SimpleStringProperty();
	private int grapples;
	StringProperty grappleProperty = new SimpleStringProperty();
	Label alertLabel               = new Label();
	StringProperty alertProperty   = new SimpleStringProperty();
	
	private ArrayList<Label> itemCounts;
	
	private WorldView viewPointer;
	
	private PlayerObject player;
	
	public Inventory(WorldView view, PlayerObject player) {
		super(backgroundImage);
		
		viewPointer = view;
		this.player = player;
		
		teeth = 0;
		sticks = 0;
		spears = 0;
		vines = 0;
		grapples = 0;
		
		this.setX(0);
		this.setY(0);
		this.setFitWidth(WorldView.SCENE_SIZE);
		this.setFitHeight(WorldView.SCENE_SIZE + MenuBar.MENUBAR_HEIGHT);
		makeExitButton();
		makeBuildingButtons();
		makeDisplayNumbers();
		
	}
	
	public int getTeeth() {
		return teeth;
	}
	
	public int getSticks() {
		return sticks;
	}
	
	public int getSpears() {
		return spears;
	}
	
	public int getVines() {
		return vines;
	}
	
	public int getGrapples() {
		return grapples;
	}
	
	public void addTeeth(int numTeeth) {
		teeth += numTeeth;
		toothProperty.set("" + teeth);
		updateSpearButton();
	}
	
	public void removeTeeth(int numTeeth) {
		addTeeth(-numTeeth);
	}
	
	public void addSticks(int numSticks) {
		sticks += numSticks;
		stickProperty.set("" + sticks);
		updateSpearButton();
	}
	
	public void removeSticks(int numSticks) {
		addSticks(-numSticks);
	}
	
	public void addSpears(int numSpears) {
		player.setImagePath("file:src/images/ughhhSpearSpriteSheet.png");
		player.setHasSpear();
		spears += numSpears;
		spearProperty.set("" + spears);
		updateGrappleButton();
	}
	
	public void removeSpears(int numSpears) {
		addSpears(-numSpears);
	}
	
	public void addVines(int numVines) {
		vines += numVines;
		vineProperty.set("" + vines);
		updateGrappleButton();
	}
	
	public void removeVines(int numVines) {
		addVines(-numVines);
	}
	
	public void addGrapples(int numGrapples) {
		grapples += numGrapples;
		grappleProperty.set("" + grapples);
	}
	
	public void removeGrapples(int numGrapples) {
		addGrapples(-numGrapples);
	}
	
	private void makeExitButton() {	
		Image exitButtonImage = new Image("file:src/images/x.png");
		exitButton = new ImageView(exitButtonImage);
		exitButton.setOnMouseClicked(new CloseInventory());
			
		exitButton.setX(this.getFitWidth() - 30);
		exitButton.setY(5);
		exitButton.setFitHeight(25);
		exitButton.setFitWidth(25);
	}
	
	class CloseInventory implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			alertProperty.set("");
			viewPointer.remove(getObjects());
			//Unpause the game
			viewPointer.getGameClock().play();
		}
	}
	
	private void makeBuildingButtons() {
		
		alertLabel.textProperty().bind(alertProperty);
		alertLabel.setLayoutX(155);
		alertLabel.setLayoutY(6);
		alertLabel.setFont(new Font(16));
		alertLabel.setTextFill(Color.RED);
		
		buildSpearButton   = new ImageView(inactiveBuild);
		buildGrappleButton = new ImageView(inactiveBuild);
		
		buildSpearButton.setOnMouseClicked(new BuildSpear());
		buildGrappleButton.setOnMouseClicked(new BuildGrapple());
		
		buildSpearButton.setX(245);
		buildSpearButton.setY(495);
		buildSpearButton.setFitWidth(30);
		buildSpearButton.setFitHeight(30);
		
		buildGrappleButton.setX(245);
		buildGrappleButton.setY(535);
		buildGrappleButton.setFitWidth(30);
		buildGrappleButton.setFitHeight(30);
	}
	
	class BuildSpear implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			alertProperty.set("");
			if (teeth > 0 && sticks > 0) {
				//Then create the spear
				removeTeeth(1);
				removeSticks(1);
				addSpears(1);
			} else {
				//Then block spear creation
				alertProperty.set("You need a tooth and a stick to make a spear.");
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
		if (teeth > 0 && sticks > 0) {
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
		Label toothLabel = new Label();
		Label stickLabel = new Label();
		Label spearLabel = new Label();
		Label vineLabel = new Label();
		Label grappleLabel = new Label();
		
		
		toothProperty.set("" + teeth);
		toothLabel.textProperty().bind(toothProperty);
		toothLabel.setLayoutX(390);
		toothLabel.setLayoutY(65);
		toothLabel.setFont(new Font(30));
		toothLabel.setTextFill(Color.CRIMSON);
		
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
		grappleLabel.setTextFill(Color.CRIMSON);
		
		
		itemCounts.add(toothLabel);
		itemCounts.add(stickLabel);
		itemCounts.add(spearLabel);
		itemCounts.add(vineLabel);
		itemCounts.add(grappleLabel);
		
		
	}
	
	public ArrayList<Node> getObjects() {
		ArrayList<Node> localObjects = new ArrayList<Node>();
		//Putting this first guarantees that the Inventory is drawn before buttons
		localObjects.add(this);
		localObjects.add(exitButton);
		localObjects.add(buildSpearButton);
		localObjects.add(buildGrappleButton);
		localObjects.add(alertLabel);
		localObjects.addAll(itemCounts);
		return localObjects;
	}
}
