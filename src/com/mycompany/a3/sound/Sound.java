package com.mycompany.a3.sound;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

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
