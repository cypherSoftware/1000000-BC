package model;

import java.util.ArrayList;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view.map.WorldView;
public class Minimap extends Parent {
	
	private static Image backgroundImage = new Image("file:src/images/Minimap.png");
	private ImageView exitButton;
	private Canvas minimap;
	private GraphicsContext context;
	

	
	private WorldView viewPointer;
	
	public Minimap(WorldView view) {
		minimap = new Canvas(500, 300);
		minimap.setLayoutX(WorldView.SCENE_SIZE / 10);
		minimap.setLayoutY(WorldView.SCENE_SIZE / 4);
		context = minimap.getGraphicsContext2D();
		context.drawImage(backgroundImage, 0, 0);
		
		viewPointer = view;

		makeExitButton();
		//drawMap(); Called by view now
		
	}
	
	private void makeExitButton() {	
		Image exitButtonImage = new Image("file:src/images/x.png");
		exitButton = new ImageView(exitButtonImage);
		exitButton.setOnMouseClicked(new CloseMinimap());
			
		exitButton.setX(minimap.getLayoutX() + minimap.getWidth() - 30);
		exitButton.setY(minimap.getLayoutY() + 5);
		exitButton.setFitHeight(25);
		exitButton.setFitWidth(25);
	}
	
	class CloseMinimap implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent mousePressed) {
			viewPointer.remove(getObjects());
			//Unpause the game
			viewPointer.getGameClock().play();
			
		}
	}
	
	public void drawMap() {
		
		context.setFill(Color.LIGHTGOLDENRODYELLOW);
		context.fillRect(getMinimapXCoordsByIndex(0), getMinimapYCoordsByIndex(0), 173 - 155, 173 - 155);
		for (int i = 0; i < MapGenerator.WORLD_SIZE; i++) {
			for (int j = 0; j < MapGenerator.WORLD_SIZE; j++) {
				if (viewPointer.getWorldMap()[j][i] == 0) {
					//Impassable
					context.setFill(Color.BLACK);
				} else if (viewPointer.getWorldMap()[j][i] == 1) {
					//Clearing
					context.setFill(Color.DARKOLIVEGREEN);
				} else if (viewPointer.getWorldMap()[j][i] == 2) {
					//Connecting Path
					context.setFill(Color.GREEN);
				} else if (viewPointer.getWorldMap()[j][i] == 3) {
					//Cavern
					context.setFill(Color.BROWN);
				} else if (viewPointer.getWorldMap()[j][i] == 8) {
					//River
					context.setFill(Color.BLUE);
				} else {
					//Also a clearing
					context.setFill(Color.DARKOLIVEGREEN);
				}
				if (viewPointer.getController().getCurrentX() == i &&
					viewPointer.getController().getCurrentY() == j) {
					//Current Screen
					context.setFill(Color.LIGHTGOLDENRODYELLOW);
				}
				context.fillRect(getMinimapXCoordsByIndex(i), getMinimapYCoordsByIndex(j), 173 - 155, 173 - 155);
			}
		}
		
	}
	
	private int getMinimapXCoordsByIndex(int index){
		int baseX = 151;
		int squareSize = 175 - 152;
		int offset = 155 - 151;
		return baseX + index * squareSize + offset;
	}
	
	private int getMinimapYCoordsByIndex(int index) {
		int baseY = 13;
		int squareSize = 37 - 14;
		int offset = 17 - 13;
		return baseY + index*squareSize + offset;
	}
	

	public ArrayList<Node> getObjects() {
		ArrayList<Node> localObjects = new ArrayList<Node>();
		//Putting this first guarantees that the Inventory is drawn before buttons
		localObjects.add(minimap);
		localObjects.add(exitButton);
		return localObjects;
	}


}
