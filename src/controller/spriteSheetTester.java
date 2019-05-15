package controller;


import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class spriteSheetTester extends Application {

    private static final Image IMAGE = new Image("file:src/images/ughhh spritesheet.png"); //load sprite sheet

    private static final int ROW = 2;		//pick row on sprite sheet 
    private static final int COLUMNS  =   4;	//number of columns of animation in selected row.
    private static final int COUNT    =  4;	
    private static final int OFFSET_X =  256;
    private static final int OFFSET_Y =  256;		//moves viewport down to which row you want
    private static final int WIDTH    = 256;			//size of image block width
    private static final int HEIGHT   = 256;			//size of image block height
    final ImageView imageView = new ImageView(IMAGE);
    private SpriteSheet ss = new SpriteSheet();
    Button butt = new Button();
    String testnum = "1";
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Testing sprite Sheets");
        BorderPane gp = new BorderPane();
        
        ss.setNewAnimation(5, 4);
        butt.setText("next");
        gp.setStyle("-fx-background-color: BLACK");	//black background to verify background on sprite is really gone.
        gp.setBottom(butt);
        butt.setOnAction(e ->  imageView.setViewport(ss.nextSprite()));
      //  butt.setOnAction(e -> butt.setText(testnum += "a"));
        primaryStage.setHeight(512);
        primaryStage.setWidth(512);
       
        imageView.setViewport(ss.nextSprite());
       
       // final Animation animation = new SpriteSheet(imageView, Duration.millis(1000), COUNT, COLUMNS, OFFSET_X, OFFSET_Y, WIDTH, HEIGHT);
       //final Animation animtation2 = new Animation();
      
     //   animation.setCycleCount(Animation.INDEFINITE);
     //   animation.play();
        gp.getChildren().add(new Group(imageView));
        primaryStage.setScene(new Scene(gp)); 
        primaryStage.show();
    }
}