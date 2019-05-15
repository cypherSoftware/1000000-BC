package controller;

import javafx.scene.media.MediaPlayer;

import java.io.File;

import javafx.scene.media.Media;

/**
 * This class uses the javafx MediaPlayer object to generate music for the background of the game.
 * The reason why we're using this class around the original MediaPlayer object is because we want
 * to be able to keep the same song playing if a new screen is pushed using the same music.
 * This helps keep the WorldView class clean by eliminating the MediaPlayer syntax necessary to
 * do that, so that the view only has to worry about getting the ScreenMap's intended music,
 * and passing it here to make sure it gets played.
 * @author DavidMinch
 *
 */
public class BackgroundMusic {
	private MediaPlayer musicPlayer;
	private String currentSong;
	private boolean paused;
	
	public BackgroundMusic() {
		currentSong = "src/music/game3.wav"; // Default to menu
		Media nowPlaying = new Media(new File(currentSong).toURI().toString());
		musicPlayer = new MediaPlayer(nowPlaying);
		paused = true;
	}
	
	public void setSong(String songPath) {
		if (!currentSong.equals(songPath)) {
			//Then we need to change the song
			paused = true;
			musicPlayer.pause();
			currentSong = songPath;
			Media newSong = new Media(new File(currentSong).toURI().toString());
			musicPlayer.dispose();
			musicPlayer = new MediaPlayer(newSong);
		}
		//If we were paused, then play
		if (paused) {
			musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			musicPlayer.play();
		}
	}
	
	public void pauseMusic() {
		paused = true;
		musicPlayer.pause();
	}
}
