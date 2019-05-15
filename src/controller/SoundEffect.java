package controller;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

import javafx.scene.media.Media;

/**
 * This class uses the javafx MediaPlayer object to generate sound effects for the game.
 * The reason why we're using this class around the original MediaPlayer object is so that an object
 *    SoundEffect squelch = new SoundEffect("src/effects/squelch.mp3");
 *    squelch.play();
 * to get a sound effect easily. These should be listed as private variables for the class that makes this sound,
 * and the sound.play() should be in the method where the action occurs. 
 * @author DavidMinch
 *
 */
public class SoundEffect {
	private MediaPlayer musicPlayer;

	
	public SoundEffect(String effectSource) {
		Media nowPlaying = new Media(new File(effectSource).toURI().toString());
		musicPlayer = new MediaPlayer(nowPlaying);
		musicPlayer.setAutoPlay(false);
	}
	public void play() {
		musicPlayer.seek(Duration.ZERO);
		musicPlayer.play();
		
	}

}
