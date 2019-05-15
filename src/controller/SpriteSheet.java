package controller;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SpriteSheet {


	private int count = 0;
	private final int offsetX;
	private final int width;
	private final int height;
	private int maxOffsetX;
	// private int startRow;
	private int numFrames;
	int currentX;
	int currentY;
	boolean isFirstCycle = false;

	public SpriteSheet() {

		this.numFrames = 1;
		this.offsetX   = 256;
		this.width     = 256;
		this.height    = 256;
		this.currentY  = 0;
		this.currentX  = 0;
		this.maxOffsetX = 0;
		// this.imageView.setViewport(new Rectangle2D(0, currentY, width, height));
	}

	public void setNewAnimation(int numFrames, int startRow) {

		this.numFrames = numFrames;
		maxOffsetX = (numFrames -1) * 256;
		currentX = 0;
		currentY = startRow *256;
		isFirstCycle = true;

	}

	public void setOffsetx(int x) {

	}

	public void setOffsety(int y) {

	}

	/* public Rectangle2D startSprite() {
    	return new Rectangle2D(0, currentY, width, height);
    }*/

	public Rectangle2D nextSprite() {
		if(currentX == maxOffsetX) {
			currentX = 0;
			return interpolate();
		}
		return	interpolate();
	}

	//I took out double k for interpolate()
	protected Rectangle2D interpolate() {


		if(count != numFrames - 1) {
			//if (index != lastIndex) {
			currentX += offsetX;
			count ++;
		}
		else {
			count = 0;
			currentX = 0;
		}
		return new Rectangle2D(currentX, currentY, width, height);

	}

	/*  public SpriteSheet(ImageView imageView, Duration duration, int count,   int columns, int offsetX, int offsetY, int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(Animation.INDEFINITE);
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    public void setOffsetx(int x) {

    }

    public void setOffsety(int y) {

    }

    public Rectangle2D nextSprite() {
    return	interpolate();
    }

    	//I took out double k for interpolate()
    protected Rectangle2D interpolate() {
        final int index = Math.min((int) Math.floor(count), count - 1);
        //if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
           // imageView.setViewport(
            	return new Rectangle2D(x, y, width, height);
           // lastIndex = index;
       // }
    }*/
}

