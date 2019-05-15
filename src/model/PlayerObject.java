package model;

import java.util.ArrayList;

import enums.ID;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import view.map.WorldView;

public class PlayerObject extends GameObject{

	private boolean hasSpear = false;
	private boolean isAttacking = false;
	private char directionFacing = 'D';
	
	int hp = 50;
	
	private Inventory inventory;
	private TitleScreen titleStart;
	/*private boolean isFacingUp = false;
	private boolean isFacingDown = true;
	private boolean isFacingLeft = false;
	private boolean isFacingRight = false;*/
	
	
	
	public PlayerObject(String path, int x, int y, ID id, WorldView view) {
		super(path, x, y, id, view);
		inventory = new Inventory(view, this);
		titleStart = new TitleScreen( view, this.getIsInvincible());
		
	}
	
	
	public void setIsAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}
	
	public void setHasSpear() {
		this.hasSpear = true;
	}
	
	public boolean hasSpear() {
		return hasSpear;
	}
	
	
	
	public char getDirectionFacing() {
		return directionFacing;
	}
		
	public void setDirectionFacing(char direction) {
		directionFacing = direction;
	}
	
	public void collision() {
		
	}
	
	@Override
	public Rectangle getHitBox() {
		double xStartCoord = this.getX();	//x coordinate for upper corner of hitBox
		double yStartCoord = this.getY();	//y coordinate for upper corner of hit box
		
		//if not attacking then return standard hitBox
		
			//System.out.println("box without attacking has been returned");
			
			Rectangle rNA =  new Rectangle(xStartCoord,yStartCoord, 48, 48);
			return rNA;
			//return new Rectangle(xStartCoord,yStartCoord, 48, 48);
			
		
		
	}
	
	@Override
	public Rectangle getAttackBox() {
		double xStartCoord = this.getX();	//x coordinate for upper corner of hitBox
		double yStartCoord = this.getY();	//y coordinate for upper corner of hit box
		
		if(isAttacking) {
		if (getDirectionFacing() == 'U') {
				return new Rectangle(xStartCoord ,yStartCoord - 48, 48, 48);
		}
		else
			if (getDirectionFacing() == 'R') {
				return new Rectangle(xStartCoord + 48, yStartCoord, 48, 48);
			}
		else
			if(getDirectionFacing() == 'L') {
				return new Rectangle(xStartCoord - 48,yStartCoord, 48, 48);
			}
		else
			if(getDirectionFacing() == 'D') {
				return new Rectangle(xStartCoord,yStartCoord + 48, 48, 48);
			}
		}
		
		return new Rectangle(0,0, 0, 0); //if not attacking no attack box returned
	
	
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	
	public TitleScreen getTitleScreen() {
		return titleStart;
	}

}
