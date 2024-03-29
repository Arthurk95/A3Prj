package com.mycompany.a3.sound;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

/* Loops a sound */
public class BGSound implements Runnable{
	private Media m;
	public BGSound(String fileName) {
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), 
							"/"+fileName);
		
			m = MediaManager.createMedia(is, "audio/wav", this);
		
			m.setVolume(20);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pause() { m.pause(); }
	public void play() { m.play(); }
	public void run() {
		m.setTime(0);
		m.play();
	}
}
