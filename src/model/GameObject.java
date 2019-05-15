package model;

import java.util.ArrayList;

import controller.SpriteSheet;
import enums.ID;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.map.WorldView;

/**
 * 
 * A class to hold the data associated with objects in the game
 * @author Matt Burns
 *
 */
 
public class GameObject extends ImageView{
	// Current x and y coordinates are covered by imageview: getX(), getY(), setX(), setY()
	
	// Direction of movement. Under most circumstances, should be -1, 0, or 1
	private int dx;
	private int dy;
	protected ID id;
	private int hp;
	private int maxhp = 100;
	public static boolean invincible = false;
	private DoubleProperty healthPercentage = new SimpleDoubleProperty(1.0);
	public SpriteSheet spriteSheet = new SpriteSheet(); 

	private char directionFacing = 'D';
	
	
	private Image img;
	
	// The game tick on which this object will move. Lower numbers = higher speed
	private int speed;
	
	// The path to the tile that stores the image for this object
	private String imgPath;
	
	protected WorldView viewPointer;
	
	public GameObject(String path, int x, int y, ID id, WorldView view) {
		this.setImagePath(path);
		this.viewPointer = view;
		this.setXbyCoord(x);
		this.setYbyCoord(y);
		this.id = id;
		this.setHP(this.maxhp);
	}
	
	public GameObject(Image i, int x, int y, ID id, WorldView view) {
		this.img = i;
		this.viewPointer = view;
		this.setXbyCoord(x);
		this.setYbyCoord(y);
		this.id = id;
		this.setHP(this.maxhp);
		this.setImage(img);
		
	}

	public char getDirectionFacing() {
		return directionFacing;
	}
		
	public void setDirectionFacing(char direction) {
		directionFacing = direction;
	}
	
	public int getHP() {
		return hp;
	}
	
	public int getMaxHP() {
		return maxhp;
	}
	
	public boolean getIsInvincible() {
		return this.invincible;
	}
	
	public DoubleProperty getPercentHP() {
		return healthPercentage;
	}
	
	public void setInvincibility() {
		this.invincible = true;
		
	}
	
	public void setHP(int newHP) {
		hp = newHP;
		healthPercentage.set(100 * ((double)hp / (double)maxhp));
	}
	
	public ID getID() {
		return id;
	}
	
	public void setXbyCoord(int x) {
		this.setX(x * ScreenMap.RENDER_WIDTH);
	}
	
	public void setYbyCoord(int y) {
		this.setY(y * ScreenMap.RENDER_WIDTH);
	}
	
	public void changePos(int dx, int dy) {
		this.setX(this.getX() + dx * this.speed);
		this.setY(this.getY() + dy * this.speed);
		
	}
	
	public void setSpeed(int s) {
		this.speed = s;
	}
	
	public void setXMove(int xMove) {
		this.dx = xMove;
		
	}
		
	public void setYMove(int yMove) {
		this.dy = yMove;
	}
	
	public String imgPath() {
		return imgPath;
	}
	
	public int X() {
		return (int)this.getX();
	}
	
	public int Y() {
		return (int)this.getY();
	}
	
	public int dX() {
		return dx;
	}
	
	public int dY() {
		return dy;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	
	public void setImagePath(String imagePath) {
		if (!imagePath.equals(this.imgPath)) {
			//Then update the image
			this.imgPath = imagePath;
			this.setImage(new Image(this.imgPath));
		}
	}
	
	public Rectangle getHitBox() {
		
		Rectangle r = new Rectangle(this.getX(),this.getY(), 48, 48);
		//r.setFill(Color.RED);
		return r;
	}
	
	public Rectangle getAttackBox() {
		/*double xStartCoord = this.getX();	//x coordinate for upper corner of hitBox
		double yStartCoord = this.getY();	//y coordinate for upper corner of hit box
		
		if(this.getID() == ID.Player)
		if(isAttacking) {
			if (isFacingUp) {
				return new Rectangle(xStartCoord ,yStartCoord - 48, 48, 48);
			}
			else
			if (isFacingRight) {
				return new Rectangle(xStartCoord + 48, yStartCoord, 48, 48);
			}
			else
			if(isFacingLeft) {
				return new Rectangle(xStartCoord - 48,yStartCoord, 48, 48);
			}
			else
			if(isFacingDown) {
				return new Rectangle(xStartCoord,yStartCoord + 48, 48, 48);
			}
		}
		*/
		return new Rectangle(0,0, 0, 0); //if not attacking no attack box returned
	
	
	}
	
	//Call to remove a game object from the view. 
	public void destroy() {
		ArrayList<Node> destroyMe = new ArrayList<Node>();
		destroyMe.add(this);
		viewPointer.remove(destroyMe);
	}
		

}