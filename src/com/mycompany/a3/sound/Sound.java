/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * -------------------- */

package com.mycompany.a3.sound;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

/* Plays a sound once */
public class Sound {
	private Media m;
	public Sound(String fileName) {
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);
			m = MediaManager.createMedia(is, "audio/wav");
			m.setVolume(50);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		m.setTime(0); // play from beginning
		m.play();
	}
	
	public void stop() {
		m.pause();
	}
}
