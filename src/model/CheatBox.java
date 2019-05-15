package model;
import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheatBox {

	
	private static String inputCode = "";
	public static void displayCheatBox() {
	
		Stage window = new Stage();
		
		
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("CHEAT BOX");
		window.setMinWidth(450);
		window.setMinHeight(253);
		
		Rectangle up = new Rectangle();
		up.setX(50);
		up.setY(50);
		up.setWidth(38);
		up.setHeight(42);
		up.setArcWidth(20);
		up.setArcHeight(20);
		up.setFill(new Color(1.0,1.0,1.0,0.0));
		//up.setFill(Color.ANTIQUEWHITE);
		
		Rectangle right = new Rectangle();
		right.setX(50);
		right.setY(50);
		right.setWidth(38);
		right.setHeight(42);
		right.setArcWidth(20);
		right.setArcHeight(20);
		right.setFill(new Color(1.0,1.0,1.0,0.0));
		//right.setFill(Color.ANTIQUEWHITE);
		
		Rectangle dwn = new Rectangle();
		dwn.setX(50);
		dwn.setY(50);
		dwn.setWidth(38);
		dwn.setHeight(42);
		dwn.setArcWidth(20);
		dwn.setArcHeight(20);
		dwn.setFill(new Color(1.0,1.0,1.0,0.0));
		//dwn.setFill(Color.ANTIQUEWHITE);
		
		Rectangle left = new Rectangle();
		left.setX(50);
		left.setY(50);
		left.setWidth(38);
		left.setHeight(42);
		left.setArcWidth(20);
		left.setArcHeight(20);
		left.setFill(new Color(1.0,1.0,1.0,0.0));
		//left.setFill(Color.ANTIQUEWHITE);
		
		Circle bButton = new Circle();
		bButton.setRadius(30);
		bButton.setFill(new Color(1.0,1.0,1.0,0.0));
		//bButton.setFill(Color.ANTIQUEWHITE);
		
		Circle aButton = new Circle();
		aButton.setRadius(30);
		aButton.setFill(new Color(1.0,1.0,1.0,0.0));
		//aButton.setFill(Color.ANTIQUEWHITE);
		
		Ellipse startButt = new Ellipse();
		startButt.setRadiusX(35);
		startButt.setRadiusY(15);
		startButt.setFill(new Color(1.0,1.0,1.0,0.0));
		//startButt.setFill(Color.ANTIQUEWHITE);
		
		
		Label label = new Label();
		label.setText("Cheat Box: Put in the secret code");
		label.setTextFill(Color.WHITE);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> window.close());
		Label label2 = new Label();
		label2.setText(inputCode);
		label2.setStyle("-fx-background-color: RED");
		HBox hbox = new HBox();
		
		 Image controller = new Image("file:src/images/classicNes.png");
		ImageView nesController = new ImageView(controller);
		 BorderPane endGameWindow = new BorderPane();
		//endGameWindow.getChildren().addAll(label, closeButton);
		//endGameWindow.setAlignment(Pos.CENTER);
		endGameWindow.setStyle("-fx-background-color: DIMGRAY");
		endGameWindow.setTop(label);
		endGameWindow.setAlignment(label, Pos.CENTER);
		hbox.getChildren().addAll(label2, closeButton);
		endGameWindow.setBottom(hbox);
		endGameWindow.setAlignment(hbox, Pos.CENTER);
		
		//endGameWindow.setCenter(nesController);
		AnchorPane anchor = new AnchorPane();
		endGameWindow.setCenter(anchor);
		anchor.setStyle("-fx-background-color: RED");
		
		StackPane controllerPic = new StackPane();
		//upButton.getChildren().add(up);
		anchor.getChildren().add(controllerPic);
		//anchor.setLeftAnchor(upButton, 10d);
		//anchor.setBottomAnchor(upButton, 10d);
		//upButton.setStyle("-fx-border-color:white");
		controllerPic.getChildren().addAll(nesController);
		
		StackPane upButton = new StackPane();
		upButton.getChildren().add(up);
		anchor.getChildren().add(upButton);
		anchor.setLeftAnchor(upButton, 80d);
		anchor.setBottomAnchor(upButton, 115d);
		up.setOnMouseClicked(e -> label2.setText(inputCode += "U"));
		
		StackPane buttonB = new StackPane();
		buttonB.getChildren().add(bButton);
		anchor.getChildren().add(buttonB);
		anchor.setLeftAnchor(buttonB, 390d);
		anchor.setBottomAnchor(buttonB, 40d);
		bButton.setOnMouseClicked(e -> label2.setText(inputCode += "B"));
		
		StackPane buttonA = new StackPane();
		buttonA.getChildren().add(aButton);
		anchor.getChildren().add(buttonA);
		anchor.setLeftAnchor(buttonA, 470d);
		anchor.setBottomAnchor(buttonA, 40d);
		aButton.setOnMouseClicked(e -> label2.setText(inputCode += "A"));
		
		StackPane downButton = new StackPane();
		downButton.getChildren().add(dwn);
		anchor.getChildren().add(downButton);
		anchor.setLeftAnchor(downButton, 80d);
		anchor.setBottomAnchor(downButton, 40d);
		dwn.setOnMouseClicked(e -> label2.setText(inputCode += "D"));
		
		StackPane leftButton = new StackPane();
		leftButton.getChildren().add(left);
		anchor.getChildren().add(leftButton);
		anchor.setLeftAnchor(leftButton, 40d);
		anchor.setBottomAnchor(leftButton, 80d);
		left.setOnMouseClicked(e -> label2.setText(inputCode += "L"));
		
		StackPane rightButton = new StackPane();
		rightButton.getChildren().add(right);
		anchor.getChildren().add(rightButton);
		anchor.setLeftAnchor(rightButton, 120d);
		anchor.setBottomAnchor(rightButton, 80d);
		right.setOnMouseClicked(e -> label2.setText(inputCode += "R"));
		
		StackPane startButton = new StackPane();
		startButton.getChildren().add(startButt);
		anchor.getChildren().add(startButton);
		anchor.setLeftAnchor(startButton, 285d);
		anchor.setBottomAnchor(startButton, 55d);
		startButt.setOnMouseClicked(e -> {
											if(inputCode.equals("UUDDLRLRBA")) {
												System.out.println("Cheat successful");
												 GameObject.invincible = true;
											}
											window.close();
											});
		
		
		Scene scene = new Scene(endGameWindow);
		window.setScene(scene);
		window.showAndWait();
	}
}