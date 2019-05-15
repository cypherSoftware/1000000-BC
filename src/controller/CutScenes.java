package controller;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CutScenes { //extends Application {

	private MediaView victoryCutScene;
	private MediaPlayer mediaplayer;
	private String cutScene1 = "src/images/finalCutSceneOogachaka.mp4";
	private String cutScene2 = "src/images/fordingTheRiver.mp4";
	private String cutSceneToPlay;
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}*/

	
		
	

	/*@Override
	public void start(Stage primaryStage) throws Exception {*/
		
	
		
		public void showCutScene (int cutSceneNum) {
		
			Stage primaryStage = new Stage();
			
			if (cutSceneNum == 1) {
				cutSceneToPlay = cutScene1;
			}
			else {
				cutSceneToPlay = cutScene2;
			}
			
		final Media m = new Media(new File(cutSceneToPlay).toURI().toString()); //this.getClass().getResource("finalCutsceneOogachaka.mp4").toExternalForm());
		
		final MediaPlayer mp = new MediaPlayer(m);
		final MediaView mv = new MediaView(mp);
		
		final DoubleProperty width = mv.fitWidthProperty();
		final DoubleProperty height = mv.fitHeightProperty();
		
		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
		
		//Status status = mp.getStatus();
		mv.setPreserveRatio(true);
		mp.setAutoPlay(true);
		mp.setOnEndOfMedia( new Runnable() {
			public void run() {primaryStage.close();
		}});
		
		
		StackPane root = new StackPane();
		root.getChildren().add(mv);
		
		final Scene scene = new Scene(root, 9660, 540);
		scene.setFill(Color.BLACK);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Cut Scene");
		primaryStage.setFullScreen(true);
		primaryStage.show();
		
		
		
	}

}

